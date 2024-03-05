package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.servicehandler.NoFlyZoneServiceHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NoFlyZoneControllerTest {

    @Mock
    private NoFlyZoneServiceHandler noFlyZoneServiceHandler;

    @Mock
    private EllipsoidNoFlyZone ellipsoidNoFlyZone;

    @Mock
    private PolygonNoFlyZone polygonNoFlyZone;

    @Mock
    RectangleNoFlyZone rectangleNoFlyZone;

    private NoFlyZoneController noFlyZoneController;

    @Before
    public void setUp() {
        noFlyZoneController = new NoFlyZoneController(noFlyZoneServiceHandler);
    }

    @Test
    public void addEllipsoidNoFlyZoneShouldCallServiceHandler() {
        noFlyZoneController.addEllipsoidNoFlyZone(ellipsoidNoFlyZone);

        verify(noFlyZoneServiceHandler, times(1)).handleEllipsoid(ellipsoidNoFlyZone);
    }

    @Test
    public void addPolygonNoFLyZoneShouldCallServiceHandler() {
        noFlyZoneController.addPolygonNoFlyZone(polygonNoFlyZone);

        verify(noFlyZoneServiceHandler, times(1)).handlePolygon(polygonNoFlyZone);
    }

    @Test
    public void addRectangleNoFlyZoneShouldCallServiceHandler() {
        noFlyZoneController.addRectangleNoFlyZone(rectangleNoFlyZone);

        verify(noFlyZoneServiceHandler, times(1)).handleRectangle(rectangleNoFlyZone);
    }

    @Test
    public void deleteNoFlyZoneShouldCallServiceHandler() {
        noFlyZoneController.deleteNoFlyZone("noFlyZone");

        verify(noFlyZoneServiceHandler, times(1)).deleteNoFlyZone("noFlyZone");
    }
}
