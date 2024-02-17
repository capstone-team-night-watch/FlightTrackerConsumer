package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 * Bindings class used for representing Rectangle no-fly zone data
 */

@Data
public class RectangleNoFlyZone {
    private String name;

    private float westLongDegree;

    private float eastLongDegree;

    private float southLatDegree;

    private float northLatDegree;

    private float rotationDegree;

    private float maxAltitude;

    private float minAltitude;
}
