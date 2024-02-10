package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.bindings.GetNoFlyZoneConflictResponse;
import com.capstone.consumer.servicehandler.GetFlightLocationServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to getting a flights location
 */
@RestController
public class GetFlightLocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFlightLocationController.class);

    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final GetFlightLocationServiceHandler serviceHandler;

    public GetFlightLocationController(GetFlightLocationServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    /**
     * Sets up the request mapping for getting a flights location (Ex. NEBRASKA)
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @return A Response Object representing the location a flight is above
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getFlightLocation")
    public GetFlightLocationResponse getFlightLocation(
            @RequestParam() String longitude,
            @RequestParam() String latitude
    ) {
        LOGGER.info("Received request to acquire location of flight with the following coordinates - " +
                "longitude: {}, latitude: {}", longitude, latitude);

        return serviceHandler.handleFlightLocation(longitude, latitude);
    }

    /**
     * Sets up the request mapping for determining if a flight is in a no-fly zone
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @return A Response Object representing the no-fly zone a flight is in
     */
    @CrossOrigin(origins = "*")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/getInNoFlyZone"
    )
    @ResponseBody
    public GetNoFlyZoneConflictResponse getInNoFlyZone(
            @RequestParam() Double longitude,
            @RequestParam() Double latitude,
            @RequestParam() Double altitude
    ) {
        LOGGER.info("Received request to determine if flight is in conflict with no-fly zone. " +
                        "Coords that will be checked against DB are longitude: {}, latitude: {}, and altitude: {}",
                longitude, latitude, altitude);

        return serviceHandler.handleNoFlyConflict(longitude, latitude, altitude);
    }
}
