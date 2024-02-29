package com.capstone.consumer.servicehandler;

import com.capstone.consumer.beans.TfrBean;
import com.capstone.consumer.bindings.GetNoFlyZonesResponse;
import com.capstone.consumer.repository.Repository;
import org.springframework.stereotype.Service;

/**
 * Service Handler class that facilitates making the necessary Repository calls related to getting no-fly zones
 */
@Service
public class GetNoFlyZonesServiceHandler {

    /**
     * The Repository Object that facilitates the database interaction
     */
    private final Repository repository;

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

        response.setEllipsoidNoFlyZones(repository.getEllipsoidNoFlyZones());
        response.setMilitaryNoFlyZones(repository.getMilitaryNoFlyZones());

        return response;
    }

    /**
     * Makes the necessary Repository calls to acquire all the no-fly zones currently in the database
     *
     * @return A Response Object representing all the no-fly zones
     */
    public GetNoFlyZonesResponse handleTfr() {
        GetNoFlyZonesResponse response = new GetNoFlyZonesResponse();
        response.setTfrNoFlyZones(TfrBean.getAllTfr());

        return response;
    }
}
