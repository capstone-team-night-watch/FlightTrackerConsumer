package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.serviceHandler.NoFlyZoneServiceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddNoFlyZoneControllerTest {
    @Mock
    private NoFlyZoneServiceHandler serviceHandler;

    @InjectMocks
    private NoFlyZoneController addNoFlyZoneController;

    @Test
    public void shouldCallServiceEllipsoid() {
        addNoFlyZoneController.addEllipsoidNoFlyZone(new EllipsoidNoFlyZone());

        verify(serviceHandler).handleEllipsoid(any());
    }

    @Test
    public void shouldCallServicePolygon() {
        addNoFlyZoneController.addPolygonNoFlyZone(new PolygonNoFlyZone());

        verify(serviceHandler).handlePolygon(any());
    }

    @Test
    public void shouldCallServiceRectangle() {
        addNoFlyZoneController.addRectangleNoFlyZone(new RectangleNoFlyZone());

        verify(serviceHandler).handleRectangle(any());
    }
}
