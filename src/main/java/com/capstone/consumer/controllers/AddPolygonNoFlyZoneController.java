package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.serviceHandler.AddEllipsoidNoFlyZoneServiceHandler;
import com.capstone.consumer.serviceHandler.AddPolygonNoFlyZoneServiceHandler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddPolygonNoFlyZoneController {

    private AddPolygonNoFlyZoneServiceHandler serviceHandler;

    public AddPolygonNoFlyZoneController(AddPolygonNoFlyZoneServiceHandler serviceHandler){
        this.serviceHandler = serviceHandler;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/polygon"
    )
    @ResponseBody
    public String addPolygonNoFlyZone(@RequestBody PolygonNoFlyZone polygonNoFlyZone){
        serviceHandler.handle(polygonNoFlyZone);
        return "add new no fly zone = " + polygonNoFlyZone.toString();
    }
}
