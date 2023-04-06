package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.serviceHandler.AddEllipsoidNoFlyZoneServiceHandler;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddEllipsoidNoFlyZoneController {

    private AddEllipsoidNoFlyZoneServiceHandler serviceHandler;

    public AddEllipsoidNoFlyZoneController(AddEllipsoidNoFlyZoneServiceHandler serviceHandler){
        this.serviceHandler = serviceHandler;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            path = "addNoFlyZone/ellipsoid"
    )
    @ResponseBody
    public String addEllipsoidNoFlyZone(@RequestBody EllipsoidNoFlyZone ellipsoidNoFlyZone) {

        serviceHandler.handle(ellipsoidNoFlyZone);
        return "add new no fly zone = " + ellipsoidNoFlyZone.toString();

    }

}
