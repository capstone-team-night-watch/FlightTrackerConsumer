package com.capstone.shared.bindings;

import lombok.Data;

@Data
public class Airport {
    private String name;
    private String icaoCode;
    private GeographicCoordinates2D coordinates;
}
