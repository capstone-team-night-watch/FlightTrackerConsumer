package com.capstone.consumer.bindings;

import lombok.Data;


/*
 * Describes geographic coordinates of a point
 */
@Data
public class GeographicCoordinates3D {
    private double latitude;

    private double longitude;

    private double altitude;

    GeographicCoordinates3D() {
    }
}