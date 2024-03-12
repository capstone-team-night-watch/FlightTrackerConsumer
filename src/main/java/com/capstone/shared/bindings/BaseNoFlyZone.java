package com.capstone.shared.bindings;

import com.capstone.shared.enums.NoFlyZoneType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseNoFlyZone {
    protected float altitude;

    @NotEmpty
    protected String notamNumber;

    protected Date CreatedAt;

    protected String description;

    protected NoFlyZoneType type;

    public String getId() {
        return "notam-" + notamNumber;
    }
}
