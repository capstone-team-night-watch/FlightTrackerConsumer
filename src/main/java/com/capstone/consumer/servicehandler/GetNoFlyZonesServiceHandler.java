package com.capstone.consumer.servicehandler;

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
}
