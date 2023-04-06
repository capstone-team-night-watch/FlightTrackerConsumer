package com.capstone.consumer.serviceHandler;

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

    public String handle(final String longitude, final String latitude) {
        LOGGER.warn("HANDLER LONG: " + longitude);
        return repository.getFlightLocation(longitude, latitude);
    }
}
