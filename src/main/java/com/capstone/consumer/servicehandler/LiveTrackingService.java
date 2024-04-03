package com.capstone.consumer.servicehandler;

import java.util.List;
import java.util.ArrayList;

import com.capstone.consumer.config.MessagingService;
import com.capstone.consumer.messages.*;
import org.webjars.NotFoundException;
import com.capstone.consumer.utils.GeoUtils;
import org.springframework.stereotype.Component;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.consumer.bindings.FlightCollision;
import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.FlightPathCollision;
import com.capstone.shared.bindings.FlightInformationKafkaDto;


@Component
public class LiveTrackingService {
    private final List<BaseNoFlyZone> noFlyZones = new ArrayList<>();

    private final List<FlightInformation> flights = new ArrayList<>();
    private final MessagingService messagingService;

    public List<FlightInformation> getAllCurrentFlight() {
        return flights;
    }

    public List<BaseNoFlyZone> getAllNoFlyZones() {
        return noFlyZones;
    }

    public LiveTrackingService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void trackNewNoFlyZone(BaseNoFlyZone noFlyZone) {
        noFlyZones.add(noFlyZone);
        messagingService.sendMessage(new NoFlyZoneCreatedMessage(noFlyZone));

        for (var flight : flights) {
            var intersection = GeoUtils.getFlightIntersectionWithNoFlyZone(flight, noFlyZone);

            if (!intersection.isEmpty()) {
                handleFlightPathCollision(flight, noFlyZone);
            }
        }
    }

    public void upsertFlightInformation(FlightInformationKafkaDto newFlightLocation) {
        var existingFlightLocation = flights.stream()
                .filter(flightLocation -> flightLocation.getFlightId().equals(newFlightLocation.getFlightId()))
                .findFirst();

        if (existingFlightLocation.isEmpty()) {
            createInternalFlight(newFlightLocation);
            return;
        }

        updateFlight(newFlightLocation);
    }

    /**
     * Responds to the creation of a new flight received from kafka
     *
     * @param flightInformationDto information about the new flight that has been created
     */
    public void createInternalFlight(FlightInformationKafkaDto flightInformationDto) {
        var newFlightInformation = new FlightInformation()
                .setFlightId(flightInformationDto.getFlightId())
                .setLocation(flightInformationDto.getLocation())
                .setGroundSpeed(flightInformationDto.getGroundSpeed())
                .setHeading(flightInformationDto.getHeading())
                .setCheckPoints(flightInformationDto.getCheckPoints());

        this.flights.add(newFlightInformation);

        // Notify any listener that a new flight has been created
        messagingService.sendMessage(new FlightCreatedMessage(newFlightInformation));

        verifyFlightPath(newFlightInformation);
        verityFlightIsInNoFlyZone(newFlightInformation);
    }

    public void updateFlight(FlightInformationKafkaDto flightInformationKafkaDto) {
        var targetFlight = flights.stream()
                .filter(x -> x.getFlightId().equals(flightInformationKafkaDto.getFlightId()))
                .findFirst();

        if (targetFlight.isEmpty()) {
            throw new NotFoundException("Flight with id " + flightInformationKafkaDto.getFlightId() + " not found");
        }

        if (flightInformationKafkaDto.getCheckPoints() != null) {
            targetFlight.get().setCheckPoints(flightInformationKafkaDto.getCheckPoints());
            messagingService.sendMessage(new FlightLocationUpdatedMessage(targetFlight.get()));

            verifyFlightPath(targetFlight.get());
        }

        if (flightInformationKafkaDto.getLocation() != null) {
            targetFlight.get().setLocation(flightInformationKafkaDto.getLocation());
            messagingService.sendMessage(new FlightLocationUpdatedMessage(targetFlight.get()));

            verityFlightIsInNoFlyZone(targetFlight.get());
        }

        targetFlight.get()
                .setDestination(flightInformationKafkaDto.getDestination())
                .setHeading(flightInformationKafkaDto.getHeading());
    }

    public void verityFlightIsInNoFlyZone(FlightInformation flightInformation) {
        flightInformation.setFlightCollisions(new ArrayList<>());

        for (var noFlyZone : noFlyZones) {
            if (!GeoUtils.flightIsWithinNoFlyZone(flightInformation, noFlyZone)) continue;

            var message = new FlightEnteredNoFlyZoneMessage(flightInformation, noFlyZone);

            messagingService.sendMessage(message);

            flightInformation.getFlightCollisions().add(
                    new FlightCollision()
                            .setFlightId(flightInformation.getFlightId())
                            .setNoFlyZone(noFlyZone.getId())
            );
        }
    }

    /**
     * Performs verification on the flight path to ensure that it is not colliding with any of the no fly zones that are being tracked
     *
     * @param flightInformation flight information that must be verified
     */
    private void verifyFlightPath(FlightInformation flightInformation) {
        flightInformation.setFlightPathCollisions(new ArrayList<>());

        for (var noFlyZone : noFlyZones) {
            var intersection = GeoUtils.getFlightIntersectionWithNoFlyZone(flightInformation, noFlyZone);

            flightInformation.getFlightPathCollisions().add(
                    new FlightPathCollision()
                            .setNoFlyZone(noFlyZone.getId())
                            .setFlightId(flightInformation.getFlightId())
                            .setLocation("Unknown")
            );

            if (intersection.isEmpty()) {
                continue;
            }

            if (!intersection.isEmpty()) {
                handleFlightPathCollision(flightInformation, noFlyZone);
            }
        }
    }

    private void handleFlightPathCollision(FlightInformation flightInformation, BaseNoFlyZone noFlyZone) {
        var message = new FlightPathIntersectWithNoFlyZoneMessage(flightInformation, noFlyZone);
        messagingService.sendMessage(message);
    }
}
