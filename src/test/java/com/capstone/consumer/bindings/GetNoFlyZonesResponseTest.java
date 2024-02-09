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
            .name("NAME")
            .westLongDegree(1)
            .eastLongDegree(1)
            .southLatDegree(1)
            .northLatDegree(1)
            .rotationDegree(1)
            .maxAltitude(1)
            .minAltitude(1);

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE = new PolygonNoFlyZone()
            .name("NAME")
            .vertex1Long(1)
            .vertex1Lat(1)
            .vertex2Long(1)
            .vertex2Lat(1)
            .vertex3Long(1)
            .vertex3Lat(1)
            .vertex4Long(1)
            .vertex4Lat(1)
            .maxAltitude(1)
            .minAltitude(1);

    private final static EllipsoidNoFlyZone ELLIPSOID_NO_FLY_ZONE = new EllipsoidNoFlyZone()
            .name("NAME")
            .longitude(1)
            .latitude(1)
            .altitude(1)
            .longRadius(1)
            .latRadius(1)
            .altRadius(1);

    private final static MilitaryNoFlyZone MILITARY_NO_FLY_ZONE =
            new MilitaryNoFlyZone("NAME", "GEOJSON");


    @Test
    public void setAndGetEllipsoidNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setEllipsoidNoFlyZones(List.of(ELLIPSOID_NO_FLY_ZONE));
        EllipsoidNoFlyZone ellipsoidNoFlyZone = getNoFlyZonesResponse.getEllipsoidNoFlyZones().get(0);

        assertEquals("NAME", ellipsoidNoFlyZone.name());
    }

    @Test
    public void setAndGetRectangleNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setRectangleNoFlyZones(List.of(RECTANGLE_NO_FLY_ZONE));
        RectangleNoFlyZone rectangleNoFlyZone = getNoFlyZonesResponse.getRectangleNoFlyZones().get(0);

        assertEquals("NAME", rectangleNoFlyZone.name());
    }

    @Test
    public void setAndGetPolygonNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setPolygonNoFlyZones(List.of(POLYGON_NO_FLY_ZONE));
        PolygonNoFlyZone polygonNoFlyZone = getNoFlyZonesResponse.getPolygonNoFlyZones().get(0);

        assertEquals("NAME", polygonNoFlyZone.name());
    }

    @Test
    public void setAndGetMilitaryNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setMilitaryNoFlyZones(List.of(MILITARY_NO_FLY_ZONE));
        MilitaryNoFlyZone militaryNoFlyZone = getNoFlyZonesResponse.getMilitaryNoFlyZones().get(0);

        assertEquals("NAME", militaryNoFlyZone.name());
    }

}