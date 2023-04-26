package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
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

    @CrossOrigin(origins = "*")
    @GetMapping("/getFlightLocation")
    public GetFlightLocationResponse getFlightLocation(
            @RequestParam() String longitude,
            @RequestParam() String latitude
    ){
        LOGGER.warn(latitude);
        LOGGER.warn("LONG:" + longitude);
        GetFlightLocationResponse location = serviceHandler.handle(longitude, latitude);
        return location;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            path = "/getInNoFlyZone"
    )
    @ResponseBody
    public GetNoFlyZoneConflictResponse getInNoFlyZone(
            @RequestParam() Double longitude,
            @RequestParam() Double latitude,
            @RequestParam() Double altitude
    ) {
        return serviceHandler.handleNoFlyConflict(longitude, latitude, altitude);
    }
}
