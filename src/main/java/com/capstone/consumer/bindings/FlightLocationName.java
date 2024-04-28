package com.capstone.consumer.bindings;

import lombok.Data;

import java.util.Date;

@Data
public class FlightLocationName {
    private Date date;

    private String name;

    private String country;
}
