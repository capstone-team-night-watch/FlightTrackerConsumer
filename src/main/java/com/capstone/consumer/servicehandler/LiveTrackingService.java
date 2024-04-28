package com.capstone.consumer.servicehandler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.capstone.consumer.bindings.FlightLocationName;
import com.capstone.consumer.config.MessagingService;
import com.capstone.consumer.messages.*;
import com.capstone.geocode.ReverseGeoCode;
import org.webjars.NotFoundException;
import com.capstone.consumer.utils.GeoUtils;
import org.springframework.stereotype.Component;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.consumer.bindings.FlightCollision;
import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.FlightPathCollision;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import com.capstone.shared.bindings.GeographicCoordinates3D;
import com.google.common.collect.Lists;


@Component
public class LiveTrackingService {
    private final ReverseGeoCode reverseGeoCode;

    private final MessagingService messagingService;

    private final List<BaseNoFlyZone> noFlyZones = new ArrayList<>();

    private final List<FlightInformation> flights = new ArrayList<>();

    public List<FlightInformation> getAllActiveFlight() {
        return flights;
    }

    public List<BaseNoFlyZone> getAllNoFlyZones() {
        return noFlyZones;
    }

    public LiveTrackingService(ReverseGeoCode reverseGeoCode, MessagingService messagingService) {
        this.reverseGeoCode = reverseGeoCode;
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

        for (var flight : flights) {
            var flightIsWithinNoFlyZone = GeoUtils.flightIsWithinNoFlyZone(flight, noFlyZone);

            if (flightIsWithinNoFlyZone) {
                handleFlightEnteredNoFlyZone(flight, noFlyZone);
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
    private void createInternalFlight(FlightInformationKafkaDto flightInformationDto) {
        var location = flightInformationDto.getLocation();

        var newFlightInformation = new FlightInformation()
                .setSource(flightInformationDto.getSource())
                .setHeading(flightInformationDto.getHeading())
                .setFlightId(flightInformationDto.getFlightId())
                .setLocation(flightInformationDto.getLocation())
                .setSource(flightInformationDto.getDestination())
                .setGroundSpeed(flightInformationDto.getGroundSpeed())
                .setHeading(flightInformationDto.getHeading())
                .setCheckPoints(flightInformationDto.getCheckPoints())
                .setSource(flightInformationDto.getSource())
                .setDestination(flightInformationDto.getDestination())
                .setRealFlightPath(Lists.newArrayList(new GeographicCoordinates3D()
                        .setAltitude(location.getAltitude())
                        .setLatitude(location.getLatitude())
                        .setLongitude(location.getLongitude())
                ));

        var locationName = reverseGeoCode.nearestPlace(
                newFlightInformation.getLocation().getLatitude(),
                newFlightInformation.getLocation().getLongitude()
        );

        newFlightInformation.setFlightLocationName(new FlightLocationName()
                .setName(locationName.getName())
                .setCountry(locationName.getCountry())
                .setDate(Date.from(Instant.now()))
        );

        this.flights.add(newFlightInformation);

        // Notify any listener that a new flight has been created
        messagingService.sendMessage(new FlightCreatedMessage(newFlightInformation));

        verifyFlightPath(newFlightInformation);
        verityFlightIsInNoFlyZone(newFlightInformation);
    }

    /**
     * Update flight information within the live tracker. Performs relevant post-processing such flight location updates
     * or no-fly zone intersection checks
     *
     * @param flightInformationKafkaDto dto containing possible partial information about the new flight
     */
    private void updateFlight(FlightInformationKafkaDto flightInformationKafkaDto) {

        var targetFlight = flights.stream()
                .filter(x -> x.getFlightId().equals(flightInformationKafkaDto.getFlightId()))
                .findFirst();

        if (targetFlight.isEmpty()) {
            throw new NotFoundException("Flight with id " + flightInformationKafkaDto.getFlightId() + " not found");
        }

        if (flightInformationKafkaDto.getCheckPoints() != null) {
            targetFlight.get().setSource(flightInformationKafkaDto.getSource());
            targetFlight.get().setCheckPoints(flightInformationKafkaDto.getCheckPoints());
            targetFlight.get().setDestination(flightInformationKafkaDto.getDestination());

            messagingService.sendMessage(new FlightPathUpdatedMessage(targetFlight.get()));

            verifyFlightPath(targetFlight.get());
        }

        if (flightInformationKafkaDto.getLocation() != null) {
            targetFlight.get().setHeading(flightInformationKafkaDto.getHeading());
            targetFlight.get().setLocation(flightInformationKafkaDto.getLocation());
            targetFlight.get().setGroundSpeed(flightInformationKafkaDto.getGroundSpeed());
            targetFlight.get().getRealFlightPath().add(flightInformationKafkaDto.getLocation());

            updateFlightLocationName(targetFlight.get());

            messagingService.sendMessage(new FlightLocationUpdatedMessage(targetFlight.get()));

            verityFlightIsInNoFlyZone(targetFlight.get());
        }

        if (flightInformationKafkaDto.getGroundSpeed() != null) {
            targetFlight.get().setGroundSpeed(flightInformationKafkaDto.getGroundSpeed());
        }
    }

    /**
     * Verifies is a flight is within one of the currently tracked no-fly-zones and adds the intersection information to the
     * intersection and path intersection parameters contained within the flight
     *
     * @param flightInformation flight information that must be checked for intersection with no-fly-zone
     */
    private void verityFlightIsInNoFlyZone(FlightInformation flightInformation) {
        flightInformation.setFlightCollisions(new ArrayList<>());

        for (var noFlyZone : noFlyZones) {
            if (!GeoUtils.flightIsWithinNoFlyZone(flightInformation, noFlyZone)) {
                continue;
            }

            handleFlightEnteredNoFlyZone(flightInformation, noFlyZone);
        }
    }

    /**
     * Update the flight location information if it is necessary. That is if the update delay threshold has past
     *
     * @param flightInformation flight information whose location information must be updated
     */
    private void updateFlightLocationName(FlightInformation flightInformation) {
        var difference = flightInformation.getFlightLocationName().getDate().toInstant().until(Instant.now(), ChronoUnit.SECONDS);

        if (difference > 5) {
            var locationName = reverseGeoCode.nearestPlace(
                    flightInformation.getLocation().getLatitude(),
                    flightInformation.getLocation().getLongitude()
            );

            flightInformation.getFlightLocationName().setName(locationName.getName());
            flightInformation.getFlightLocationName().setCountry(locationName.getCountry());
            flightInformation.getFlightLocationName().setDate(Date.from(Instant.now()));
        }
    }

    /**
     * Performs verification on the flight path to ensure that it is not colliding with any of the no-fly-zones that are being tracked
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

    private void handleFlightEnteredNoFlyZone(FlightInformation flightInformation, BaseNoFlyZone noFlyZone) {
        var message = new FlightEnteredNoFlyZoneMessage(flightInformation, noFlyZone);

        messagingService.sendMessage(message);

        flightInformation.getFlightCollisions().add(
                new FlightCollision()
                        .setFlightId(flightInformation.getFlightId())
                        .setNoFlyZone(noFlyZone.getId())
        );
    }

}
