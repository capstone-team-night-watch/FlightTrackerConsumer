package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddRectangleNoFlyZoneServiceHandler {
    //private static final Logger LOGGER = LoggerFactory.getLogger(getFlightLocationServiceHandler.class);

    private final Repository repository;

    public AddRectangleNoFlyZoneServiceHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(RectangleNoFlyZone noFlyZone) { repository.addRectangleNoFlyZone(noFlyZone); }
}