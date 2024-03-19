package com.capstone.consumer;

import com.capstone.consumer.utils.GeoUtils;
import com.capstone.shared.bindings.CircularNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;
import com.capstone.shared.bindings.GeographicCoordinates2D;
import com.capstone.shared.bindings.PolygonNoFlyZone;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollisionTest {
    @Test
    public void detectCollision_shouldDetectCollision_whenPathAreColliding() {
        var newTestFlightInformation = new FlightInformation()
                .setCheckPoints(List.of(
                        new GeographicCoordinates2D(-72.2787738, 42.9363492),
                        new GeographicCoordinates2D(-72.2777867, 42.9330187)
                ));


        var newTestNoFlyZone = new PolygonNoFlyZone()
                .setVertices(List.of(
                        new GeographicCoordinates2D(-72.2752762, 42.9363178),
                        new GeographicCoordinates2D(-72.2808123, 42.9351553),
                        new GeographicCoordinates2D(-72.2802114, 42.9315733),
                        new GeographicCoordinates2D(-72.2750616, 42.9323589)
                ));


        Assert.assertTrue(GeoUtils.flightIsIntersectingWithNoFlyZone(newTestFlightInformation, newTestNoFlyZone));
    }

    @Test
    public void detectCollision_shouldDetectCollision_whenPathAreCollidingWithCircularNoFlyZone() {
        var newTestFlightInformation = new FlightInformation()
                .setCheckPoints(List.of(
                        new GeographicCoordinates2D(-72.2787738, 42.9363492),
                        new GeographicCoordinates2D(-72.2777867, 42.9330187)
                ));


        var newTestNoFlyZone = new CircularNoFlyZone()
                .setCenter(new GeographicCoordinates2D(-72.2781086, 42.9341027))
                .setRadius(1000);


        Assert.assertTrue(GeoUtils.flightIsIntersectingWithNoFlyZone(newTestFlightInformation, newTestNoFlyZone));
    }

    @Test
    public void detectCollision_shouldNotDetectCollision_whenPathAreCollidingWithCircularNoFlyZone() {
        var newTestFlightInformation = new FlightInformation()
                .setCheckPoints(List.of(
                        new GeographicCoordinates2D(-72.2787738, 42.9363492),
                        new GeographicCoordinates2D(-72.2777867, 42.9330187)
                ));


        var newTestNoFlyZone = new CircularNoFlyZone()
                .setCenter(new GeographicCoordinates2D(-72.2962189, 42.9270171))
                .setRadius(10);


        Assert.assertFalse(GeoUtils.flightIsIntersectingWithNoFlyZone(newTestFlightInformation, newTestNoFlyZone));
    }

    @Test
    public void detectCollision_shouldDetectCollision_ShouldNotDetectCollisionWhenPathDoNotCollide() {
        var newTestFlightInformation = new FlightInformation()
                .setCheckPoints(List.of(
                        new GeographicCoordinates2D(-72.2761774, 42.9375588),
                        new GeographicCoordinates2D(-72.2819066, 42.9363178)
                ));


        var newTestNoFlyZone = new PolygonNoFlyZone()
                .setVertices(List.of(
                        new GeographicCoordinates2D(-72.2752762, 42.9363178),
                        new GeographicCoordinates2D(-72.2808123, 42.9351553),
                        new GeographicCoordinates2D(-72.2802114, 42.9315733),
                        new GeographicCoordinates2D(-72.2750616, 42.9323589)
                ));


        Assert.assertFalse(GeoUtils.flightIsIntersectingWithNoFlyZone(newTestFlightInformation, newTestNoFlyZone));
    }
}
