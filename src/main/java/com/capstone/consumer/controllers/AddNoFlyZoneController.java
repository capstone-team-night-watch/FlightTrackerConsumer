package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.serviceHandler.AddNoFlyZoneServiceHandler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles all endpoints related to adding no-fly zones
 */
@RestController
public class AddNoFlyZoneController {

    /**
     * Service Handler Object that facilitates the logic that needs to happen when a request is received
     */
    private final AddNoFlyZoneServiceHandler serviceHandler;

    public AddNoFlyZoneController(AddNoFlyZoneServiceHandler serviceHandler) {
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
}
