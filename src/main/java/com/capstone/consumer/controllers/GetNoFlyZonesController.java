package com.capstone.consumer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.capstone.consumer.bindings.GetNoFlyZonesResponse;
import com.capstone.consumer.servicehandler.GetNoFlyZonesServiceHandler;

/**
 * Controller class that handles all endpoints related to acquiring no-fly zones
 */
@RestController
public class GetNoFlyZonesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetNoFlyZonesController.class);

    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final GetNoFlyZonesServiceHandler serviceHandler;

    public GetNoFlyZonesController(GetNoFlyZonesServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }


    /**
     * Sets up the request mapping for getting all no-fly zones
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @return A Response Object representing all the different-typed no-fly zones contained in the database
     */
    @CrossOrigin(origins = "*")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/get-no-fly-zones"
    )
    @ResponseBody
    public GetNoFlyZonesResponse getNoFlyZones() {
        LOGGER.info("Received Request to get no fly zones");

        return serviceHandler.handle();
    }

    

    /**
     * Sets up request mapping and request params for delete no-fly zone service
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param zoneName The name of the zone to be deleted
     * @return String of deleted no-fly zone
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getTfrNoFlyZones")
    @ResponseBody
    public GetNoFlyZonesResponse getTfrNoFlyZones() {
        LOGGER.info("Received Request to get TFR no fly zones");

        return serviceHandler.handleTfr();
    }
}
