package com.capstone.consumer.controllers;

import com.capstone.consumer.servicehandler.GetNoFlyZonesServiceHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZonesControllerTest {

    private GetNoFlyZonesController getNoFlyZonesController;

    @Mock
    private GetNoFlyZonesServiceHandler getNoFlyZonesServiceHandler;

    @Before
    public void setUp() {
        getNoFlyZonesController = new GetNoFlyZonesController(getNoFlyZonesServiceHandler);
    }

    @Test
    public void getNoFlyZonesShouldCallServiceHandler() {
        getNoFlyZonesController.getNoFlyZones();

        verify(getNoFlyZonesServiceHandler, times(1)).handle();
    }

}