package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.GeographicCoordinates3D;
import lombok.Data;

@Data
public class FlightLocationUpdatedMessage {
    /**
     * Unique identifier for the flight
     */
    private String flightId;

    /**
     * Current location of the plane
     */
    private GeographicCoordinates3D location;


    /**
     * Describes the group speed of the plan in miles per second
     */
    private float groundSpeed;


    /**
     * Describes the general direction of the plane
     * TODO: Find how this is represented on the aero api
     */
    private float heading;
}
