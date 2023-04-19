package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.serviceHandler.AddPolygonNoFlyZoneServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddPolygonNoFlyZoneControllerTest {

    @Mock
    private AddPolygonNoFlyZoneServiceHandler serviceHandler;

    @InjectMocks
    private AddPolygonNoFlyZoneController controller;

    @Test
    public void shouldCallService(){
        controller.addPolygonNoFlyZone(new PolygonNoFlyZone());

        verify(serviceHandler).handle(any());
    }
}
