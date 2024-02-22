package com.capstone.consumer.bindings;

import lombok.Data;


/**
 * Bindings class used for representing Flight Location (region) data
 */
@Data
public class GetFlightLocationResponse {
    private String location;

    public GetFlightLocationResponse(String location) {
        this.location = location;
    }
}
