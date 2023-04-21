package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.FlightLocation;
import com.capstone.consumer.bindings.GetNoFlyZoneConflictResponse;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.serviceHandler.getFlightLocationServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@RestController
public class GetFlightLocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFlightLocationController.class);

    private final getFlightLocationServiceHandler serviceHandler;

    public GetFlightLocationController(getFlightLocationServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getFlightLocation")
    public String getFlightLocation(
            @RequestParam() String longitude,
            @RequestParam() String latitude
    ){
        LOGGER.warn(latitude);
        LOGGER.warn("LONG:" + longitude);
        String location = serviceHandler.handle(longitude, latitude);
        return "Plane is located over " + location;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getInNoFlyZone")
    public GetNoFlyZoneConflictResponse getInNoFlyZone(
            @RequestParam() String longitude,
            @RequestParam() String latitude,
            @RequestParam() String altitude
    ) {
        return serviceHandler.handleNoFlyConflict(longitude, latitude, altitude);
    }
}
