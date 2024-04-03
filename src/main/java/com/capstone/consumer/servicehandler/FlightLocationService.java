package com.capstone.consumer.servicehandler;


import com.capstone.shared.bindings.GeographicCoordinates3D;
import com.google.common.collect.Lists;

import com.capstone.shared.bindings.FlightInformationKafkaDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FlightLocationService {
    private final List<FlightInformationKafkaDto> flightInformationStore = Collections.synchronizedList(new ArrayList<FlightInformationKafkaDto>());


    /**
     * Updates flight information if it already exists or create a new one if not
     *
     * @param newFlightLocation flight information to be updated or inserted
     */
    public void upsertFlightInformation(FlightInformationKafkaDto newFlightLocation) {
        var existingFlightLocation = flightInformationStore.stream()
                .filter(flightLocation -> flightLocation.getFlightId().equals(newFlightLocation.getFlightId()))
                .findFirst();

        if (existingFlightLocation.isEmpty()) {
            GeographicCoordinates3D location = newFlightLocation.getLocation();
            flightInformationStore.add(newFlightLocation
                                            .setRealFlightPath(Lists.newArrayList(new GeographicCoordinates3D()
                                                                                .setAltitude(location.getAltitude())
                                                                                .setLatitude(location.getLatitude())
                                                                                .setLongitude(location.getLongitude()) )
                                                                        )
                                        );
            return;
        }


        existingFlightLocation.get()
                .setHeading(newFlightLocation.getHeading())
                .setFlightId(newFlightLocation.getFlightId())
                .setLocation(newFlightLocation.getLocation())
                .setGroundSpeed(newFlightLocation.getGroundSpeed())
                .getRealFlightPath().add(newFlightLocation.getLocation());

    }

    public List<FlightInformationKafkaDto> getActiveFlight() {
        return flightInformationStore;
    }
}
