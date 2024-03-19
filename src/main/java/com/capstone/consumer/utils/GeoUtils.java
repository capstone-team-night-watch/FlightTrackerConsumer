package com.capstone.consumer.utils;

import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;
import com.capstone.shared.bindings.GeographicCoordinates2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.util.GeometricShapeFactory;

public class GeoUtils {
    public static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    private GeoUtils() {
    }

    public static boolean flightIsIntersectingWithNoFlyZone(FlightInformation flightInformation, BaseNoFlyZone noFlyZone) {
        var pathGeometry = flightInformation.getFlightPathGeometry();
        var noFlyZoneGeometry = noFlyZone.getNoFlyZoneBoundariesGeometry();

        return pathGeometry.intersects(noFlyZoneGeometry);
    }

    public static boolean flightIsWithinNoFlyZone(FlightInformation flightInformation, BaseNoFlyZone noFlyZone) {
        var pathGeometry = flightInformation.getFlightPathGeometry();
        var noFlyZoneGeometry = noFlyZone.getNoFlyZoneBoundariesGeometry();

        return pathGeometry.within(noFlyZoneGeometry);
    }

    /**
     * Utility function that help the creation of circular geotools geometries
     *
     * @param geographicCoordinates2D Center of the circle in geographical coordinates
     * @param diameterInMeters        diameter in meters of the circle
     * @return a circular geometry
     */
    public static Geometry createCircleGeometry(GeographicCoordinates2D geographicCoordinates2D, double diameterInMeters) {
        var shapeFactory = new GeometricShapeFactory();

        shapeFactory.setNumPoints(120);
        shapeFactory.setCentre(new Coordinate(
                geographicCoordinates2D.getLatitude(),
                geographicCoordinates2D.getLongitude()
        ));

        shapeFactory.setWidth(diameterInMeters / 111320d); // Length in meters of 1° of latitude = always 111.32 km
        shapeFactory.setHeight(diameterInMeters / (40075000 * Math.cos(Math.toRadians(geographicCoordinates2D.getLatitude())) / 360));// Length in meters of 0° of longitude = 40075 km * cos( latitude ) / 360

        return shapeFactory.createEllipse();
    }
}
