package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddPolygonNoFlyZoneServiceHandler {
    //private static final Logger LOGGER = LoggerFactory.getLogger(getFlightLocationServiceHandler.class);

    private final Repository repository;

    public AddPolygonNoFlyZoneServiceHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(PolygonNoFlyZone noFlyZone) {
        repository.addPolygonNoFlyZone(noFlyZone);
    }
}