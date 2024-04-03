package com.capstone.consumer.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.enums.Messages;
import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.messages.FlightCreatedMessage;
import com.capstone.consumer.messages.FlightEnteredNoFlyZoneMessage;
import com.capstone.consumer.messages.NoFlyZoneCreatedMessage;
import com.capstone.consumer.servicehandler.FlightLocationService;
import com.capstone.shared.bindings.CircularNoFlyZone;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import com.capstone.shared.bindings.PolygonNoFlyZone;
import com.corundumstudio.socketio.SingleRoomBroadcastOperations;
import com.corundumstudio.socketio.SocketIOServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class KafkaConsumerTest {
    @Autowired
    private KafkaConsumer consumer;

    @MockBean
    private SocketIOServer socketIOServer;

    @Mock
    private SingleRoomBroadcastOperations singleRoomBroadcastOperations;

    @Autowired
    private FlightLocationService flightLocationService;

    @Before
    public void setUp() {
        when(socketIOServer.getRoomOperations(anyString())).thenReturn(singleRoomBroadcastOperations);
    }

    @Test
    public void listenToRectangleNoFlyZone_ShouldNotifyWebSocket() throws IOException {
        consumer.handleCircularNoFlyZone("""
                {
                  "altitude": 1000,
                  "notamNumber": "1234",
                  "type": "CIRCLE",
                  "center": {
                    "latitude": 40,
                    "longitude": 40
                  },
                  "radius": 100
                }
                """);


        var stringCaptor = ArgumentCaptor.forClass(String.class);
        var noFlyZoneCaptor = ArgumentCaptor.forClass(NoFlyZoneCreatedMessage.class);

        verify(singleRoomBroadcastOperations).sendEvent(stringCaptor.capture(), noFlyZoneCaptor.capture());


        var room = stringCaptor.getValue();
        var noFlyZone = noFlyZoneCaptor.getValue().getNoFlyZone();

        assertEquals(Messages.NO_FLY_ZONE_CREATED, room);
        assertTrue(noFlyZone instanceof CircularNoFlyZone);

        verify(socketIOServer).getRoomOperations(stringCaptor.capture());
        assertEquals(Rooms.NO_FLY_ZONE_ROOM, stringCaptor.getValue());
    }

    @Test
    public void handlePolygonNoFlyZone_NotifyWebSocket() {
        consumer.handlePolygonNoFlyZone("""
                {
                  "altitude": 1000,
                  "notamNumber": "1234",
                  "type": "POLYGON",
                  "vertices": [
                    {
                      "latitude": 60,
                      "longitude": 50 
                    },
                    {
                      "latitude": 65,
                      "longitude": 55
                    },
                    {
                      "latitude": 55,
                      "longitude": 40
                    }
                  ]
                }
                """);


        var stringCaptor = ArgumentCaptor.forClass(String.class);
        var noFlyZoneCaptor = ArgumentCaptor.forClass(NoFlyZoneCreatedMessage.class);

        verify(singleRoomBroadcastOperations).sendEvent(stringCaptor.capture(), noFlyZoneCaptor.capture());

        var room = stringCaptor.getValue();
        var noFlyZone = noFlyZoneCaptor.getValue().getNoFlyZone();

        assertEquals(Messages.NO_FLY_ZONE_CREATED, room);
        assertTrue(noFlyZone instanceof PolygonNoFlyZone);

        verify(socketIOServer).getRoomOperations(stringCaptor.capture());
        assertEquals(Rooms.NO_FLY_ZONE_ROOM, stringCaptor.getValue());
    }

    @Test
    public void handleFlightInformation_ShouldNotifyFrontend() {
        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 1,
                             "latitude": 1,
                             "longitude": 1
                           },
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "1234",
                           "checkPoints": [2, 20,21,6 ]
                         }
                """
        );

        var stringCaptor = ArgumentCaptor.forClass(String.class);
        var flightInformationCaptor = ArgumentCaptor.forClass(FlightCreatedMessage.class);

        verify(socketIOServer, times(1)).getRoomOperations(stringCaptor.capture());

        var rooms = stringCaptor.getAllValues();

        assertThat(rooms)
                .hasSize(1)
                .contains(Rooms.FLIGHT_INFORMATION_LOBBY);

        verify(singleRoomBroadcastOperations, times(1)).sendEvent(stringCaptor.capture(), flightInformationCaptor.capture());

        var messages = stringCaptor.getAllValues();
        var noFlyZones = flightInformationCaptor.getValue();

        assertThat(messages).contains("flight-created");

        assertEquals("1234", noFlyZones.flightInformation.getFlightId());
    }

    @Test
    public void handleFlightInformation_ShouldTrackFlightInFlightService() {
        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 1,
                             "latitude": 1,
                             "longitude": 1
                           },
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "9999",
                           "checkPoints": [2, 20,21,6 ]
                         }
                """
        );

        var flightInformation = flightLocationService.getActiveFlight();

        assertThat(flightInformation)
                .extracting(FlightInformationKafkaDto::getFlightId)
                .contains("9999");
    }

    @Test
    public void handleFlightInformation_ShouldTrackFlight_Airport() {
        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 1,
                             "latitude": 1,
                             "longitude": 1
                           },
                           "source": {
                             "icaoCode": "JFK",
                             "name": "John F. Kennedy International Airport",
                             "coordinates": {
                               "latitude": 1,
                               "longitude": 1
                             }
                           },
                           "destination": {
                             "icaoCode": "JFK-Destination",
                             "name": "John F. Kennedy International Airport",
                             "coordinates": {
                               "latitude": 1,
                               "longitude": 1
                             }
                           },
                           "checkPoints": [2, 20,21, 6],
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "9999"
                         }
                """
        );

        var flightInformation = flightLocationService.getActiveFlight();

        assertThat(flightInformation)
                .extracting(FlightInformationKafkaDto::getFlightId)
                .contains("9999");

        assertEquals("JFK", flightInformation.get(0).getSource().getIcaoCode());
        assertEquals("JFK-Destination", flightInformation.get(0).getDestination().getIcaoCode());
    }


    @Test
    public void handleFlightUpdate_ShouldNotifyWhenEnters_NoFlyZone() throws IOException {
        clearInvocations(singleRoomBroadcastOperations);
        when(socketIOServer.getRoomOperations("flight-9999")).thenReturn(singleRoomBroadcastOperations);

        consumer.handleCircularNoFlyZone("""
                {
                  "altitude": 1000,
                  "notamNumber": "1234",
                  "type": "CIRCLE",
                  "center": {
                    "latitude": 40,
                    "longitude": 40
                  },
                  "radius": 100
                }
                """);


        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 10000,
                             "latitude": 40,
                             "longitude": 40
                           },
                           "source": {
                             "icaoCode": "JFK",
                             "name": "John F. Kennedy International Airport"
                            },
                           "destination": {
                             "icaoCode": "JFK-Destination",
                             "name": "John F. Kennedy International Airport"
                           },
                           "checkPoints": [2, 20,21, 6],
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "9999"
                         }
                """
        );


        var messageCaptor = ArgumentCaptor.forClass(String.class);
        var noFlyZoneCaptor = ArgumentCaptor.forClass(FlightEnteredNoFlyZoneMessage.class);

        verify(singleRoomBroadcastOperations, atLeastOnce()).sendEvent(messageCaptor.capture(), noFlyZoneCaptor.capture());

        var messages = messageCaptor.getAllValues();

        assertThat(messages).contains("flight-entered-no-fly-zone");
    }


    @Test
    public void handleFlightUpdate_ShouldNotify_WhenFlightPathCollidesWithNoFlyZone() throws IOException {
        clearInvocations(singleRoomBroadcastOperations);
        when(socketIOServer.getRoomOperations("flight-9999")).thenReturn(singleRoomBroadcastOperations);

        consumer.handleCircularNoFlyZone("""
                {
                  "altitude": 1000,
                  "notamNumber": "1234",
                  "type": "CIRCLE",
                  "center": {
                    "latitude": 40,
                    "longitude": 40
                  },
                  "radius": 100
                }
                """);


        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 10000,
                             "latitude": 70,
                             "longitude":70 
                           },
                           "source": {
                             "icaoCode": "JFK",
                             "name": "John F. Kennedy International Airport"
                            },
                           "destination": {
                             "icaoCode": "JFK-Destination",
                             "name": "John F. Kennedy International Airport"
                           },
                           "checkPoints": [70, 70 , 40, 40, 30 , 30],
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "9999"
                         }
                """
        );


        var messageCaptor = ArgumentCaptor.forClass(String.class);
        var noFlyZoneCaptor = ArgumentCaptor.forClass(FlightEnteredNoFlyZoneMessage.class);

        verify(singleRoomBroadcastOperations, atLeastOnce()).sendEvent(messageCaptor.capture(), noFlyZoneCaptor.capture());

        var messages = messageCaptor.getAllValues();

        assertThat(messages).contains("flight-path-intersect-with-no-fly-zone");
    }
}
