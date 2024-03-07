package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * Service Handler class that facilitates making Repository calls to add new no-fly zones
 */
@Service
public class NoFlyZoneServiceHandler {

    /**
     * The Repository Object that facilitates the database interaction
     */
    private final Repository repository;

    public NoFlyZoneServiceHandler(Repository repository) {
        this.repository = repository;
    }

    /**
     * Handles making the necessary Repository call to add an Ellipsoid no-fly zone
     *
     * @param noFlyZone The EllipsoidNoFlyZone Object that will be used by the Repository class
     */
    public void handleEllipsoid(EllipsoidNoFlyZone noFlyZone) {
        repository.addEllipsoidNoFlyZone(noFlyZone);
    }


    public void deleteNoFlyZone(String zoneName) {
        repository.deleteNoFlyZone(zoneName);
    }
}
