package com.capstone.shared.bindings;

import com.capstone.shared.enums.NoFlyZoneType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PolygonNoFlyZone extends BaseNoFlyZone {
    /**
     * List of vertices that make up the polygon
     */
    private List<GeographicCoordinates2D> vertices;

    public PolygonNoFlyZone() {
        this.type = NoFlyZoneType.POLYGON;
    }
}
