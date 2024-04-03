package com.capstone.consumer.entities;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

@Data
@Entity
public class FlightInformationEntity {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * comma separated list of the form "latitude,longitude,altitude"
     */
    private String location;


    /**
     * Describes the group speed of the plan in miles per second
     */
    private float groundSpeed;

    private float heading;

    private String sourceAirportName;

    private String destinationAirportName;
}
