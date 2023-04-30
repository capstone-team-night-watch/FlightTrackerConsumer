package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.serviceHandler.noFlyZoneServiceHandler;
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
    private final noFlyZoneServiceHandler serviceHandler;

    public NoFlyZoneController(noFlyZoneServiceHandler serviceHandler) {
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
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/ellipsoid"
    )
    @ResponseBody
    public String addEllipsoidNoFlyZone(@RequestBody EllipsoidNoFlyZone ellipsoidNoFlyZone) {

        serviceHandler.handleEllipsoid(ellipsoidNoFlyZone);
        return "add new no fly zone = " + ellipsoidNoFlyZone.toString();

    }

    /**
     * Sets up the request mapping for adding a Polygon no-fly zone
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param polygonNoFlyZone The Polygon no-fly zone request object
     * @return A string representing the changes that were made to the database when adding the new no-fly zone
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/polygon"
    )
    @ResponseBody
    public String addPolygonNoFlyZone(@RequestBody PolygonNoFlyZone polygonNoFlyZone) {
        serviceHandler.handlePolygon(polygonNoFlyZone);
        return "add new no fly zone = " + polygonNoFlyZone.toString();
    }

    /**
     * Sets up the request mapping for adding a Rectangle no-fly zone
     * Cross Origin scripting setup allows requests from any cross-origin script
     *
     * @param rectangleNoFlyZone The Polygon no-fly zone request object
     * @return A string representing the changes that were made to the database when adding the new no-fly zone
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/rectangle"
    )
    @ResponseBody
    public String addRectangleNoFlyZone(@RequestBody RectangleNoFlyZone rectangleNoFlyZone) {
        serviceHandler.handleRectangle(rectangleNoFlyZone);
        return "add new no fly zone = " + rectangleNoFlyZone.toString();
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
        return serviceHandler.deleteNoFlyZone(zoneName);
    }
}
