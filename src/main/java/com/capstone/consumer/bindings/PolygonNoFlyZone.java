package com.capstone.consumer.bindings;

import com.capstone.consumer.enums.NoFlyZoneType;
import lombok.*;

import java.util.List;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PolygonNoFlyZone extends BaseNoFlyZone {
    /**
     * Sample dto object for this class
     * {
     * "altitude": 1000,
     * "notamNumber": "1234",
     * "type": "POLYGON",
     * "vertices": [
     * {
     * "latitude": 1.0,
     * "longitude": 1.0
     * },
     * {
     * "latitude": 2.0,
     * "longitude": 2.0
     * },
     * {
     * "latitude": 3.0,
     * "longitude": 3.0
     * }
     * ]
     * }
     */
    private List<GeographicCoordinates2D> vertices;

    public PolygonNoFlyZone() {
        this.type = NoFlyZoneType.POLYGON;
    }
}
