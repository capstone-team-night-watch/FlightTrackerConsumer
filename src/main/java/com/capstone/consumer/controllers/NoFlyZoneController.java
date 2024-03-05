package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.servicehandler.NoFlyZoneServiceHandler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to adding/deleting no-fly zones
 */
@RestController
public class NoFlyZoneController {

    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final NoFlyZoneServiceHandler serviceHandler;

    public NoFlyZoneController(NoFlyZoneServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    /**
     * Sets up the request mapping for adding an Ellipsoid no-fly zone
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param ellipsoidNoFlyZone The Ellipsoid no-fly zone request object
     * @return A string representing the changes that were made to the database when adding the new no-fly zone
     */
    @CrossOrigin(origins = "*")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            path = "addNoFlyZone/ellipsoid"
    )
    @ResponseBody
    public String addEllipsoidNoFlyZone(@RequestBody EllipsoidNoFlyZone ellipsoidNoFlyZone) {

        serviceHandler.handleEllipsoid(ellipsoidNoFlyZone);
        return "add new no fly zone = " + ellipsoidNoFlyZone.toString();

    }

    /**
     * Sets up request mapping and request params for delete no-fly zone service
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param zoneName The name of the zone to be deleted
     * @return String of deleted no-fly zone
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/deleteNoFlyZone")
    @ResponseBody
    public String deleteNoFlyZone(@RequestParam String zoneName) {
        serviceHandler.deleteNoFlyZone(zoneName);
        return "Ok";
    }
}
