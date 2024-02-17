package com.capstone.consumer.bindings;
import lombok.Data;

/**
 * Bindings class used for representing Military no-fly zone data
 */

@Data
public class MilitaryNoFlyZone {
    private String name;
    private String geoJson;

    public MilitaryNoFlyZone(String name, String geoJson) {
        this.name = name;
        this.geoJson = geoJson;
    }
}
