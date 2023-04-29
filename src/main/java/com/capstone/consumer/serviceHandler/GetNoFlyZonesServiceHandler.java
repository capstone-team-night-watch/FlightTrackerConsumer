package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.GetNoFlyZonesResponse;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Handler class that facilitates making the necessary Repository calls related to getting no-fly zones
 */
@Service
public class GetNoFlyZonesServiceHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetNoFlyZonesServiceHandler.class);

    /**
     * The Repository Object that facilitates the database interaction
     */
    @Autowired
    private Repository repository;

    public GetNoFlyZonesServiceHandler(Repository repository) {
        this.repository = repository;
    }

    /**
     * Makes the necessary Repository calls to acquire all the no-fly zones currently in the database
     *
     * @return A Response Object representing all the no-fly zones
     */
    public GetNoFlyZonesResponse handle() {
        GetNoFlyZonesResponse response = new GetNoFlyZonesResponse();
        response.setRectangleNoFlyZones(repository.getRectangleNoFlyZones());
        response.setPolygonNoFlyZones(repository.getPolygonNoFlyZones());
        response.setEllipsoidNoFlyZones(repository.getEllipsoidNoFlyZones());
        response.setMilitaryNoFlyZones(repository.getMilitaryNoFlyZones());

        return response;
    }
}
