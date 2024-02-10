package com.capstone.consumer.bindings;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@Accessors(fluent = true)
public class PolygonNoFlyZone {
    private String name;
    private float vertex1Long;
    private float vertex1Lat;
    private float vertex2Long;
    private float vertex2Lat;
    private float vertex3Long;
    private float vertex3Lat;
    private float vertex4Long;
    private float vertex4Lat;
    private float maxAltitude;
    private float minAltitude;
}
