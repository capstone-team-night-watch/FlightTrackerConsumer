package com.capstone.shared.bindings;

import com.capstone.shared.enums.NoFlyZoneType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

import java.util.Date;

@Data
public abstract class BaseNoFlyZone {
    protected float altitude;

    @NotEmpty
    protected String notamNumber;

    protected Date createdAt;

    protected String description;

    protected NoFlyZoneType type;

    public String getId() {
        return "notam-" + notamNumber;
    }

    @JsonIgnore
    public abstract Geometry getNoFlyZoneBoundariesGeometry();
}
