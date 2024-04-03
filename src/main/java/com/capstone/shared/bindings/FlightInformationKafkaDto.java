package com.capstone.shared.bindings;

import lombok.Data;
import com.capstone.consumer.utils.GeoUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Data
public class FlightInformationKafkaDto {
    /**
     * Unique identifier for the flight
     */
    @NotNull
    private String flightId;

    /**
     * Current location of the plane
     */
    @NotNull
    private GeographicCoordinates3D location;

    /**
     * Known actual flight path of the plane
     */
    private List<GeographicCoordinates3D> realFlightPath;


    /**
     * Describes the group speed of the plan in miles per second
     */
    private float groundSpeed;


    /**
     * Describes the general direction of the plane
     */
    private float heading;


    /**
     * Source location where the flight has departed. Only specified on initial flight creation or on update
     */
    private Airport source;

    /**
     * Destination airport of the flight. Only specified on initial flight creation or on update
     */
    private Airport destination;

    /**
     * List of checkpoints that the flight must pass through. Only specified on initial flight creation or on update in lat long format
     */
    private List<Double> checkPoints;
}