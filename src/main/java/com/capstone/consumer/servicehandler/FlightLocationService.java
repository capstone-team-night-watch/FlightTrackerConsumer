package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.FlightInformation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FlightLocationService {
    private final List<FlightInformation> flightInformationStore = Collections.synchronizedList(new ArrayList<FlightInformation>());


    /**
     * Updates flight information if it already exists or create a new one if not
     * @param newFlightLocation flight information to be updated or inserted
     * @return return true if created and flase if not
     */
    public boolean upsertFlightInformation(FlightInformation newFlightLocation) {
        var existingFlightLocation = flightInformationStore.stream()
                .filter(flightLocation -> flightLocation.getFlightId().equals(newFlightLocation.getFlightId()))
                .findFirst();

        if (existingFlightLocation.isEmpty()) {
            flightInformationStore.add(newFlightLocation);
            return true;
        }

        existingFlightLocation.get()
                .setHeading(newFlightLocation.getHeading())
                .setFlightId(newFlightLocation.getFlightId())
                .setLocation(newFlightLocation.getLocation())
                .setGroundSpeed(newFlightLocation.getGroundSpeed());

        return false;
    }

    public List<FlightInformation> getActiveFlight() {
        return flightInformationStore;
    }
}
