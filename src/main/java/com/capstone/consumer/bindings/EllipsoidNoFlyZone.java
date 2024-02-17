package com.capstone.consumer.bindings;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Bindings class used for representing Ellipsoid no-fly zone data
 */
@Data
public class EllipsoidNoFlyZone {
    private String name;
    private float longitude;
    private float latitude;
    private float altitude;
    private float longRadius;
    private float latRadius;
    private float altRadius;
}
