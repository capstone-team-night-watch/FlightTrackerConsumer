package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.bindings.GetNoFlyZoneConflictResponse;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Handler class that facilitates making the necessary Repository calls related to getting a flight's location
 */
@Service
public class GetFlightLocationServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetFlightLocationServiceHandler.class);

    /**
     * The Repository Object that facilitates the database interaction
     */
    private final Repository repository;

    public GetFlightLocationServiceHandler(Repository repository) {
        this.repository = repository;
    }

    /**
     * Determines where a flight is currently above given the coordinate information
     *
     * @param longitude The longitude value of flight's current location
     * @param latitude  The latitude value of flight's current location
     * @return A Response Object representing the location a flight is above
     */
    public GetFlightLocationResponse handleFlightLocation(final String longitude, final String latitude) {
        LOGGER.warn("HANDLER LONG: {}", longitude);
        return repository.getFlightLocation(longitude, latitude);
    }

    /**
     * Determines if a flight is currently in a no-fly zone
     *
     * @param longitude The longitude value of flight's current location
     * @param latitude  The latitude value of the flight's current location
     * @param altitude  The altitude value of the flight's current location
     * @return A Response Object representing the no-fly zone that the flight is currently in contact with
     */
    public GetNoFlyZoneConflictResponse handleNoFlyConflict(Double longitude, Double latitude, Double altitude) {
        String zone = repository.getInNoFlyZoneConflict(longitude, latitude, altitude);
        GetNoFlyZoneConflictResponse noFlyZoneConflictResponse = null;

        if (zone != null) {
            noFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse(zone, true);
        } else {
            noFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse("allClear", false);
        }
        return noFlyZoneConflictResponse;
    }
}
