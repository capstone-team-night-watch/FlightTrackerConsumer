package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import com.capstone.consumer.utils.GeoUtils;
import com.capstone.shared.bindings.Airport;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import jakarta.validation.constraints.NotNull;
import com.capstone.shared.bindings.GeographicCoordinates3D;

import java.util.List;

public class FlightInformation {
    /**
     * Unique identifier for the flight
     */
    @NotNull
    @Getter
    @Setter
    private String flightId;

    /**
     * Current location of the plane
     */
    @NotNull
    @Getter
    @Setter
    private GeographicCoordinates3D location;


    /**
     * Known actual flight path of the plane
     */
    @Getter
    @Setter
    private List<GeographicCoordinates3D> realFlightPath;


    /**
     * Describes the group speed of the plan in miles per second
     */
    @Getter
    @Setter
    private float groundSpeed;


    /**
     * Describes the general direction of the plane
     */
    @Getter
    @Setter
    private float heading;


    /**
     * Source location where the flight has departed. Only specified on initial flight creation or on update
     */
    @Getter
    @Setter
    private Airport source;

    /**
     * Destination airport of the flight. Only specified on initial flight creation or on update
     */
    @Getter
    @Setter
    private Airport destination;

    @Getter
    @Setter
    private List<FlightCollision> collisions;

    /**
     * List of checkpoints that the flight must pass through. Only specified on initial flight creation or on update in lat long format
     */
    @Getter
    private List<Double> checkPoints;

    private LineString geometry;

    @Getter
    @Setter
    private  List<FlightCollision> flightCollisions;

    @Getter
    @Setter
    private  List<FlightPathCollision> flightPathCollisions;

    @Getter
    @Setter
    private FlightLocationName flightLocationName;

    public FlightInformation setCheckPoints(List<Double> checkPoints) {
        this.geometry = null;
        this.checkPoints = checkPoints;
        return this;
    }

    @JsonIgnore
    public LineString getGeometry() {
        if (this.geometry != null) {
            return this.geometry;
        }

        var pathPoints = new Coordinate[this.checkPoints.size() / 2];

        for (int i = 0; i < this.checkPoints.size(); i += 2) {
            pathPoints[i / 2] = new Coordinate(
                    this.checkPoints.get(i),
                    this.checkPoints.get(i + 1)
            );
        }

        this.geometry = GeoUtils.geometryFactory.createLineString(pathPoints);
        return this.geometry;
    }
}
