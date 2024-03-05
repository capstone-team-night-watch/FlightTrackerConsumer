package com.capstone.consumer.controllers;

import com.capstone.consumer.ApplicationProperties;
import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.bindings.HealthCheckResponse;
import com.capstone.consumer.servicehandler.GetFlightLocationServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetFlightLocationControllerTest {

    @Mock
    private GetFlightLocationServiceHandler serviceHandler;

    @Mock
    private ApplicationProperties properties;

    @InjectMocks
    private GetFlightLocationController getFlightLocationController;

    @Test
    public void shouldCallService(){
        when(serviceHandler.handleFlightLocation("long", "lat")).thenReturn(new GetFlightLocationResponse("Nebraska"));

        GetFlightLocationResponse location = getFlightLocationController.getFlightLocation("long", "lat");

        assertEquals("Nebraska", location.getLocation());
    }

    @Test
    public void returnThingsShouldReturnHealthCheckResponse() {
        when(properties.getKafkaHostUrl()).thenReturn("kafkaHostUrl");
        when(properties.getServerPort()).thenReturn("serverPort");
        HealthCheckResponse healthCheckResponse = getFlightLocationController.returnThings();

        assertNotNull(healthCheckResponse);
    }

    @Test
    public void getInNoFlyZoneShouldCallServiceHandler() {
        double coordinate = 1.0;
        getFlightLocationController.getInNoFlyZone(coordinate, coordinate, coordinate);
        verify(serviceHandler, times(1)).handleNoFlyConflict(coordinate, coordinate, coordinate);
    }
}
