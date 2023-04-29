package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.EllipsoidNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.bindings.RectangleNoFlyZone;
import com.capstone.consumer.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * Service Handler class that facilitates making Repository calls to add new no-fly zones
 */
@Service
public class AddNoFlyZoneServiceHandler {

    /**
     * The Repository Object that facilitates the database interaction
     */
    private final Repository repository;

    public AddNoFlyZoneServiceHandler(Repository repository) {
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

    /**
     * Handles making the necessary Repository call to add a Polygon no-fly zone
     *
     * @param noFlyZone The PolygonNoFlyZone Object that will be used by the Repository class
     */
    public void handlePolygon(PolygonNoFlyZone noFlyZone) {
        repository.addPolygonNoFlyZone(noFlyZone);
    }

    /**
     * Handles making the necessary Repository call to add a Rectangle no-fly zone
     *
     * @param noFlyZone The RectangleNoFlyZone Object that will be used by the Repository class
     */
    public void handleRectangle(RectangleNoFlyZone noFlyZone) {
        repository.addRectangleNoFlyZone(noFlyZone);
    }
}
