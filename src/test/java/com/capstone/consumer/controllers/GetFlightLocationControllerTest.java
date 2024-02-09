package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.servicehandler.GetFlightLocationServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFlightLocationControllerTest {

    @Mock
    private GetFlightLocationServiceHandler serviceHandler;

    @InjectMocks
    private GetFlightLocationController getFlightLocationController;

    @Test
    public void shouldCallService(){
        when(serviceHandler.handleFlightLocation("long", "lat")).thenReturn(new GetFlightLocationResponse("Nebraska"));

        GetFlightLocationResponse location = getFlightLocationController.getFlightLocation("long", "lat");

        assertEquals("Nebraska", location.location);
    }
}
