package com.capstone.shared.bindings;

import com.capstone.consumer.utils.GeoUtils;
import com.capstone.shared.enums.NoFlyZoneType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.locationtech.jts.geom.Geometry;

/**
 * Bindings class used for representing Polygon no-fly zone data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CircularNoFlyZone extends BaseNoFlyZone {
    private double radius;

    private GeographicCoordinates2D center;

    public CircularNoFlyZone() {
        this.type = NoFlyZoneType.CIRCLE;
    }

    @Override
    public Geometry getNoFlyZoneBoundariesGeometry() {
        return GeoUtils.createCircleGeometry(center, radius * 2);
    }
}
