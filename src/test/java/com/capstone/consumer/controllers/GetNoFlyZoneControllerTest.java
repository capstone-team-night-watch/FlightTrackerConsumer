package com.capstone.consumer.controllers;

import com.capstone.consumer.serviceHandler.GetNoFlyZonesServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZoneControllerTest {

    @Mock
    private GetNoFlyZonesServiceHandler service;

    @InjectMocks
    private GetNoFlyZonesController controller;

    @Test
    public void shouldCallService(){
        controller.getNoFlyZones();

        verify(service).handle();
    }
}
