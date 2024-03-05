package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NoFlyZoneServiceHandlerTest {

    @Mock
    private Repository repository;

    @Mock
    private EllipsoidNoFlyZone ellipsoidNoFlyZone;

    @Mock
    private PolygonNoFlyZone polygonNoFlyZone;

    @Mock
    private RectangleNoFlyZone rectangleNoFlyZone;

    private NoFlyZoneServiceHandler noFlyZoneServiceHandler;

    @Before
    public void setUp() {
        noFlyZoneServiceHandler = new NoFlyZoneServiceHandler(repository);
    }

    @Test
    public void handleEllipsoidShouldCallRepository() {
        noFlyZoneServiceHandler.handleEllipsoid(ellipsoidNoFlyZone);
        verify(repository, times(1)).addEllipsoidNoFlyZone(ellipsoidNoFlyZone);
    }

    @Test
    public void handlePolygonShouldCallRepository() {
        noFlyZoneServiceHandler.handlePolygon(polygonNoFlyZone);
        verify(repository, times(1)).addPolygonNoFlyZone(polygonNoFlyZone);
    }

    @Test
    public void handleRectangleShouldCallRepository() {
        noFlyZoneServiceHandler.handleRectangle(rectangleNoFlyZone);
        verify(repository, times(1)).addRectangleNoFlyZone(rectangleNoFlyZone);
    }

    @Test
    public void deleteNoFlyZoneShouldCallRepository() {
        String ZONE_TO_DELETE = "ZONE";
        noFlyZoneServiceHandler.deleteNoFlyZone(ZONE_TO_DELETE);
        verify(repository, times(1)).deleteNoFlyZone(ZONE_TO_DELETE);
    }
}