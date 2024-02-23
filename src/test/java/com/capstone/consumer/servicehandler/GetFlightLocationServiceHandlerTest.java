package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.bindings.GetNoFlyZoneConflictResponse;
import com.capstone.consumer.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetFlightLocationServiceHandlerTest {

    @Mock
    private Repository repository;

    @Mock
    private GetFlightLocationResponse getFlightLocationResponse;

    private GetFlightLocationServiceHandler getFlightLocationServiceHandler;

    @Before
    public void setUp() {
        getFlightLocationResponse = new GetFlightLocationResponse("Location");
        getFlightLocationServiceHandler = new GetFlightLocationServiceHandler(repository);
    }

    @Test
    public void handleFlightLocationShouldReturnValid() {
        when(repository.getFlightLocation(anyString(), anyString())).thenReturn(getFlightLocationResponse);

        GetFlightLocationResponse response = getFlightLocationServiceHandler.handleFlightLocation("longitude", "latitude");

        verify(repository, times(1)).getFlightLocation(anyString(), anyString());
        assertNotNull(response);
    }

    @Test
    public void handleNoFlyConflictShouldReturnConflict() {
        GetNoFlyZoneConflictResponse getNoFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse("zone", true);
        when(repository.getInNoFlyZoneConflict(anyDouble(), anyDouble(), anyDouble())).thenReturn("zone");

        GetNoFlyZoneConflictResponse response = getFlightLocationServiceHandler.handleNoFlyConflict(1.0, 1.0, 1.0);

        verify(repository, times(1)).getInNoFlyZoneConflict(anyDouble(), anyDouble(), anyDouble());
        assertEquals(getNoFlyZoneConflictResponse, response);
    }

    @Test
    public void handleNoFlyConflictShouldReturnNoConflict() {
        GetNoFlyZoneConflictResponse getNoFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse("allClear", false);
        when(repository.getInNoFlyZoneConflict(anyDouble(), anyDouble(), anyDouble())).thenReturn(null);

        GetNoFlyZoneConflictResponse response = getFlightLocationServiceHandler.handleNoFlyConflict(1.0, 1.0, 1.0);

        verify(repository, times(1)).getInNoFlyZoneConflict(anyDouble(), anyDouble(), anyDouble());
        assertEquals(getNoFlyZoneConflictResponse, response);
    }

}