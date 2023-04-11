package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddEllipsoidNoFlyZoneServiceHandler {
    //private static final Logger LOGGER = LoggerFactory.getLogger(getFlightLocationServiceHandler.class);

    private final Repository repository;

    public AddEllipsoidNoFlyZoneServiceHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(EllipsoidNoFlyZone noFlyZone) {
        repository.addEllipsoidNoFlyZone(noFlyZone);
    }
}
