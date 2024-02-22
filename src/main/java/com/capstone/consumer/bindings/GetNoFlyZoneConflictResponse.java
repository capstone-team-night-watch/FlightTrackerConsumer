package com.capstone.consumer.bindings;

import lombok.Data;


/**
 * Bindings class used for representing data relating to Flight no-fly zone conflicts
 */


@Data
public class GetNoFlyZoneConflictResponse {
    private String noFlyZoneName;
    private Boolean inConflict;

    public GetNoFlyZoneConflictResponse(String noFlyZoneName, Boolean inConflict) {
        this.noFlyZoneName = noFlyZoneName;
        this.inConflict = inConflict;
    }
}
