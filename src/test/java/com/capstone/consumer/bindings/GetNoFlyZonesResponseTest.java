package com.capstone.consumer.bindings;

import com.capstone.consumer.enums.NoFlyZoneType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetNoFlyZonesResponseTest {

    GetNoFlyZonesResponse getNoFlyZonesResponse;

    private final static PolygonNoFlyZone POLYGON_NO_FLY_ZONE = new PolygonNoFlyZone()
            .setVertices(new ArrayList<>());


    private final static CircularNoFlyZone CIRCULAR_NO_FLY_ZONE = new CircularNoFlyZone()
            .setCenter(new GeographicCoordinates2D(20, 10));

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
    public void PolygonNoFlyZone_ShouldHaveTypePolygon() {
        var polygonNoFlyZone = new PolygonNoFlyZone();
        polygonNoFlyZone.setVertices(new ArrayList<>());

        assertEquals(NoFlyZoneType.POLYGON, polygonNoFlyZone.getType());
    }


    @Test
    public void CircularNoFlyZone_ShouldHaveTypeCircular() {
        var circleNoFlyZone = new CircularNoFlyZone();

        assertEquals(NoFlyZoneType.CIRCLE, circleNoFlyZone.getType());
    }

    @Test
    public void setAndGetEllipsoidNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setEllipsoidNoFlyZones(List.of(ELLIPSOID_NO_FLY_ZONE));
        EllipsoidNoFlyZone ellipsoidNoFlyZone = getNoFlyZonesResponse.getEllipsoidNoFlyZones().get(0);

        new PolygonNoFlyZone().setVertices(new ArrayList<>());

        assertEquals("NAME", ellipsoidNoFlyZone.getName());
    }

    @Test
    public void setAndGetPolygonNoFlyZones() {
        var testNoFlyZone = new PolygonNoFlyZone()
                .setNotamNumber("123456");

        assertEquals("123456", testNoFlyZone.getNotamNumber());
    }

    @Test
    public void setAndGetMilitaryNoFlyZones() {
        getNoFlyZonesResponse = new GetNoFlyZonesResponse();
        getNoFlyZonesResponse.setMilitaryNoFlyZones(List.of(MILITARY_NO_FLY_ZONE));
        MilitaryNoFlyZone militaryNoFlyZone = getNoFlyZonesResponse.getMilitaryNoFlyZones().get(0);

        assertEquals("NAME", militaryNoFlyZone.getName());
    }

}