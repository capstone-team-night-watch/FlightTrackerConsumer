package com.capstone.consumer.bindings;

import lombok.Data;

@Data
public class FlightCollision {
    private String flightId;

    private String location;

    private String noFlyZone;
}
