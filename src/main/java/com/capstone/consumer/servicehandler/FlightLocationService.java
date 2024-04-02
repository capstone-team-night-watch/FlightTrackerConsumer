package com.capstone.consumer.servicehandler;


import com.capstone.shared.bindings.FlightInformation;
import com.capstone.shared.bindings.GeographicCoordinates3D;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
            GeographicCoordinates3D location = newFlightLocation.getLocation();
            flightInformationStore.add(newFlightLocation.setRealFlightPath(Arrays.asList(new GeographicCoordinates3D()
                                                                                .setAltitude(location.getAltitude())
                                                                                .setLatitude(location.getLatitude())
                                                                                .setLongitude(location.getLongitude()) )
                                                                        ));
            return true;
        }


        existingFlightLocation.get()
                .setHeading(newFlightLocation.getHeading())
                .setFlightId(newFlightLocation.getFlightId())
                .setLocation(newFlightLocation.getLocation())
                .setGroundSpeed(newFlightLocation.getGroundSpeed())
                .getRealFlightPath().add(newFlightLocation.getLocation());

        return false;
    }

    public List<FlightInformation> getActiveFlight() {
        return flightInformationStore;
    }
}
