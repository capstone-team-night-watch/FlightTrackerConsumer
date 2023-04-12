package com.capstone.consumer.controllers;

import com.capstone.consumer.bindings.GetNoFlyZonesResponse;
import com.capstone.consumer.bindings.TestResponse;
import com.capstone.consumer.serviceHandler.GetNoZlyZonesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class GetNoFlyZonesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetNoFlyZonesController.class);

    @Autowired
    private GetNoZlyZonesService service;

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET,
            path = "/get-no-fly-zones"
    )
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public GetNoFlyZonesResponse getNoFlyZones(){
        LOGGER.info("Received Request to get no fly zones");

        return service.handle();
    }

}
