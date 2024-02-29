package com.capstone.consumer.enums;

import com.capstone.consumer.bindings.CircularNoFlyZone;

/**
 * Describes the type of no-fly-zone supported by the system
 */
public enum NoFlyZoneType {
    /**
     * Describes a circular no-fly-zone. Circular no-fly-zones are defined by a center point, a radius and an altitude
     * @see CircularNoFlyZone
     */
    CIRCLE,

    /**
     * Describes a polygon no-fly-zone. Polygon no-fly-zones are defined by a list of points (vertices) and an altitude
     * @see com.capstone.consumer.bindings.PolygonNoFlyZone
     */
    POLYGON
}
