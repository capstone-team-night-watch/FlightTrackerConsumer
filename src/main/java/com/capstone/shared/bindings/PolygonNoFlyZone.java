package com.capstone.shared.bindings;

import com.capstone.consumer.utils.GeoUtils;
import com.capstone.shared.enums.NoFlyZoneType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.util.List;
import java.util.stream.Stream;

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

    @Override
    @JsonIgnore
    public Geometry getNoFlyZoneBoundariesGeometry() {
        var coordinates = Stream.concat(
                        this.vertices.stream(),
                        // Must add the last vertex to close the polygon because
                        // geotools does not wrap the vertices
                        Stream.of(this.vertices.get(0))
                )
                .map(geographicCoordinates2D -> new Coordinate(
                        geographicCoordinates2D.getLatitude(),
                        geographicCoordinates2D.getLongitude()
                ))
                .toArray(Coordinate[]::new);


        return GeoUtils.geometryFactory.createPolygon(coordinates);
    }
}
