package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.GetFlightLocationResponse;
import com.capstone.consumer.bindings.GetNoFlyZoneConflictResponse;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class getFlightLocationServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(getFlightLocationServiceHandler.class);

    private Repository repository;

    public getFlightLocationServiceHandler(Repository repository) {
        this.repository = repository;
    }

    public GetFlightLocationResponse handle(final String longitude, final String latitude) {
        LOGGER.warn("HANDLER LONG: " + longitude);
        return repository.getFlightLocation(longitude, latitude);
    }

    public GetNoFlyZoneConflictResponse handleNoFlyConflict(Double longitude, Double latitude, Double altitude) {
        String zone = repository.getInNoFlyZoneConflict(Double.valueOf(longitude), Double.valueOf(latitude), Double.valueOf(altitude));
        GetNoFlyZoneConflictResponse noFlyZoneConflictResponse = null;

        if(zone != null) {
            noFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse(zone, true);
        } else {
            noFlyZoneConflictResponse = new GetNoFlyZoneConflictResponse("allClear", false);
        }
        return noFlyZoneConflictResponse;
    }
}
