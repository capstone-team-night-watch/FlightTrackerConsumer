package com.capstone.consumer.bindings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZonesResponseTest {

    GetNoFlyZonesResponse getNoFlyZonesResponse;

    private final static RectangleNoFlyZone RECTANGLE_NO_FLY_ZONE = new RectangleNoFlyZone()
            .setName("NAME")
            .setWestLongDegree(1)
            .setEastLongDegree(1)
            .setSouthLatDegree(1)
            .setNorthLatDegree(1)
            .setRotationDegree(1)
            .setMaxAltitude(1)
            .setMinAltitude(1);

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE = new PolygonNoFlyZone()
            .setName("NAME")
            .setVertex1Long(1)
            .setVertex1Lat(1)
            .setVertex2Long(1)
            .setVertex2Lat(1)
            .setVertex3Long(1)
            .setVertex3Lat(1)
            .setVertex4Long(1)
            .setVertex4Lat(1)
            .setMaxAltitude(1)
            .setMinAltitude(1);

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE = new EllipsoidNoFlyZone()
            .setName("NAME")
            .setLongitude(1)
            .setLatitude(1)
            .setAltitude(1)
            .setLongRadius(1)
            .setLatRadius(1)
            .setAltRadius(1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");


    @Test
    public void setAndGetEllipsoidNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setEllipsoidNoFlyZones(List.of(ELLIPSOID_NO_FLY_ZONE));
        EllipsoidNoFlyZone ellipsoidNoFlyZone = getNoFlyZonesResponse.getEllipsoidNoFlyZones().get(0);

        assertEquals("NAME", ellipsoidNoFlyZone.getName());
    }

    @Test
    public void setAndGetRectangleNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setRectangleNoFlyZones(List.of(RECTANGLE_NO_FLY_ZONE));
        RectangleNoFlyZone rectangleNoFlyZone = getNoFlyZonesResponse.getRectangleNoFlyZones().get(0);

        assertEquals("NAME", rectangleNoFlyZone.getName());
    }

    @Test
    public void setAndGetPolygonNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setPolygonNoFlyZones(List.of(POLYGON_NO_FLY_ZONE));
        PolygonNoFlyZone polygonNoFlyZone = getNoFlyZonesResponse.getPolygonNoFlyZones().get(0);

        assertEquals("NAME", polygonNoFlyZone.getName());
    }

    @Test
    public void setAndGetMilitaryNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setMilitaryNoFlyZones(List.of(MILITARY_NO_FLY_ZONE));
        MilitaryNoFlyZone militaryNoFlyZone = getNoFlyZonesResponse.getMilitaryNoFlyZones().get(0);

        assertEquals("NAME", militaryNoFlyZone.getName());
    }

}