package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public class AddPolygonNoFlyZoneController {

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/polygon"
    )
    @ResponseBody
    public String addPolygonNoFlyZone(@RequestBody PolygonNoFlyZone polygonNoFlyZone){
        return "add new no fly zone = " + polygonNoFlyZone.toString();
    }
}
