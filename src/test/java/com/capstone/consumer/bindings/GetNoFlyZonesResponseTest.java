package com.capstone.consumer.bindings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZonesResponseTest {

    GetNoFlyZonesResponse getNoFlyZonesResponse;

    private final static RectangleNoFlyZone RECTANGLE_NO_FLY_ZONE =
            new RectangleNoFlyZone("NAME", 1, 1, 1, 1,1 ,1, 1);

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE =
            new PolygonNoFlyZone("NAME", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE =
            new EllipsoidNoFlyZone("NAME", 1, 1, 1, 1, 1, 1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");


    @Test
    public void setAndGetEllipsoidNoFlyZones(){
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setEllipsoidNoFlyZones(Arrays.asList(ELLIPSOID_NO_FLY_ZONE));
        EllipsoidNoFlyZone ellipsoidNoFlyZone = getNoFlyZonesResponse.getEllipsoidNoFlyZones().get(0);

        assertEquals("NAME", ellipsoidNoFlyZone.name);
    }

    @Test
    public void setAndGetRectangleNoFlyZones(){
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setRectangleNoFlyZones(Arrays.asList(RECTANGLE_NO_FLY_ZONE));
        RectangleNoFlyZone rectangleNoFlyZone = getNoFlyZonesResponse.getRectangleNoFlyZones().get(0);

        assertEquals("NAME", rectangleNoFlyZone.name);
    }

    @Test
    public void setAndGetPolygonNoFlyZones(){
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setPolygonNoFlyZones(Arrays.asList(POLYGON_NO_FLY_ZONE));
        PolygonNoFlyZone polygonNoFlyZone = getNoFlyZonesResponse.getPolygonNoFlyZones().get(0);

        assertEquals("NAME", polygonNoFlyZone.name);
    }

    @Test
    public void setAndGetMilitaryNoFlyZones(){
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setMilitaryNoFlyZones(Arrays.asList(MILITARY_NO_FLY_ZONE));
        MilitaryNoFlyZone militaryNoFlyZone = getNoFlyZonesResponse.getMilitaryNoFlyZones().get(0);

        assertEquals("NAME", militaryNoFlyZone.getName());
    }

}