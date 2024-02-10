package com.capstone.consumer.bindings;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Bindings class used for representing Rectangle no-fly zone data
 */

@Setter
@Getter
@Accessors(fluent = true)
public class RectangleNoFlyZone {
    private String name;

    private float westLongDegree;

    private float eastLongDegree;

    private float southLatDegree;

    private float northLatDegree;

    private float rotationDegree;

    private float maxAltitude;

    private float minAltitude;

    @Override
    public String toString() {
        return "RectangleNoFlyZone{" +
                "zone_name='" + name + '\'' +
                ", westLongDegree='" + westLongDegree + '\'' +
                ", eastLongDegree='" + eastLongDegree + '\'' +
                ", southLatDegree='" + southLatDegree + '\'' +
                ", northLatDegree='" + northLatDegree + '\'' +
                ", rotationDegree='" + rotationDegree + '\'' +
                ", maxAltitude=" + maxAltitude +
                ", minAltitude=" + minAltitude +
                '}';
    }
}
