package com.capstone.shared.bindings;

import lombok.Data;

/**
 * Represents a geographic coordinate in 2D space
 */
@Data
public class GeographicCoordinates2D {
    private double latitude;

    private double longitude;

    public GeographicCoordinates2D() {
    }

    public GeographicCoordinates2D(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
