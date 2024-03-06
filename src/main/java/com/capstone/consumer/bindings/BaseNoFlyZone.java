package com.capstone.consumer.bindings;

import com.capstone.consumer.enums.NoFlyZoneType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public abstract class BaseNoFlyZone {
    protected float altitude;

    @NotEmpty
    protected String notamNumber;

    protected NoFlyZoneType type;

    public String getId() {
        return "notam-" + notamNumber;
    }
}
