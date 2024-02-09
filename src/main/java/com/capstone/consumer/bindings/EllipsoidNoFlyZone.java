package com.capstone.consumer.bindings;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Bindings class used for representing Ellipsoid no-fly zone data
 */
@Data
@Accessors(fluent = true)
public class EllipsoidNoFlyZone {
    private String name;
    private float longitude;
    private float latitude;
    private float altitude;
    private float longRadius;
    private float latRadius;
    private float altRadius;
}
