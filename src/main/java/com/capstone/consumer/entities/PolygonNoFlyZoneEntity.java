package com.capstone.consumer.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class PolygonNoFlyZoneEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private float altitude;

    @NotEmpty
    private String notamNumber;

    private Date createdAt;

    private String description;

    // Comma separated list of waypoints
    private String wayPoints;
}
