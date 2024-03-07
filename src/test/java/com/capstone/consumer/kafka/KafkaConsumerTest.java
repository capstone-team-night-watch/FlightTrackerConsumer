package com.capstone.consumer.kafka;

import com.capstone.consumer.bindings.BaseNoFlyZone;
import com.capstone.consumer.bindings.CircularNoFlyZone;
import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.config.SocketIoService;

import static org.assertj.core.api.Assertions.assertThat;

import com.capstone.consumer.enums.Messages;
import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.messages.NoFlyZoneCreatedMessage;
import com.capstone.consumer.servicehandler.FlightLocationService;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
import com.capstone.consumer.shared.FileHelper;
import com.corundumstudio.socketio.SingleRoomBroadcastOperations;
import com.corundumstudio.socketio.SocketIOServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
    private NoFlyZoneService noFlyZoneService;

    @Autowired
    private FlightLocationService flightLocationService;

    @Before
    public void setUp() {

        when(socketIOServer.getRoomOperations(anyString())).thenReturn(singleRoomBroadcastOperations);
    }

    @Test
    public void listenToRectangleNoFlyZone_ShouldNotifyWebSocket() throws IOException {
        var testCircularNoFlyZone = FileHelper.readFile("test-circular-no-fly-zone.json");

        consumer.handleCircularNoFlyZone(testCircularNoFlyZone);


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
    public void handlePolygonNoFlyZone_NotifyWebSocket() throws IOException {
        consumer.handlePolygonNoFlyZone("""
                {
                  "altitude": 1000,
                  "notamNumber": "1234",
                  "type": "POLYGON",
                  "vertices": [
                    {
                      "latitude": 1,
                      "longitude": 1
                    },
                    {
                      "latitude": 2,
                      "longitude": 2
                    },
                    {
                      "latitude": 3,
                      "longitude": 3
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
    public void handleFlightInformation_ShouldNotifyFrontend() throws IOException {
        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 1,
                             "latitude": 1,
                             "longitude": 1
                           },
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "1234"
                         }
                """
        );

        var stringCaptor = ArgumentCaptor.forClass(String.class);
        var flightInformationCaptor = ArgumentCaptor.forClass(FlightInformation.class);

        verify(socketIOServer, times(2)).getRoomOperations(stringCaptor.capture());

        var rooms = stringCaptor.getAllValues();


        assertThat(rooms)
                .hasSize(2)
                .contains(Rooms.FLIGHT_INFORMATION_LOBBY)
                .contains("1234");

        verify(singleRoomBroadcastOperations, times(2)).sendEvent(stringCaptor.capture(), flightInformationCaptor.capture());

        var messages = stringCaptor.getAllValues();
        var noFlyZones = flightInformationCaptor.getValue();

        assertThat(messages)
                .contains(Messages.FLIGHT_CREATED)
                .contains(Messages.FLIGHT_LOCATION_UPDATED);

        assertEquals("1234", noFlyZones.getFlightId());
    }


    @Test
    public void handleFlightInformation_ShouldTrackFlightInFlightService() throws IOException {
        consumer.handleFlightUpdate("""
                         {
                           "location": {
                             "altitude": 1,
                             "latitude": 1,
                             "longitude": 1
                           },
                           "heading": 90,
                           "groundSpeed": 100,
                           "flightId": "9999"
                         }
                """
        );

        var flightInformation = flightLocationService.getActiveFlight();

        assertThat(flightInformation)
                .extracting(FlightInformation::getFlightId)
                .contains("9999");
    }
}
