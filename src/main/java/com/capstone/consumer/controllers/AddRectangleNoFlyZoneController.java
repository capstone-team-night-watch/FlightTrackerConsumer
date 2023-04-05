package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddRectangleNoFlyZoneController {

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/rectangle"
    )
    @ResponseBody
    public String addRectangleNoFlyZone(@RequestBody RectangleNoFlyZone rectangleNoFlyZone){
        return "add new no fly zone = " + rectangleNoFlyZone.toString();
    }
}
