package com.capstone.consumer.bindings;

import lombok.Data;

@Data
public class FlightPathCollision {
    private String flightId;

    private String location;

    private String noFlyZone;
}
