package com.capstone.shared.bindings;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
/*
 * Represents an airport
 */
public class Airport {
    @NotNull
    private String name;

    @NotNull
    private String icaoCode;

    @NotNull
    private GeographicCoordinates2D coordinates;
}
