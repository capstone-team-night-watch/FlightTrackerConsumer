package com.capstone.consumer.servicehandler;

import java.util.List;
import java.util.ArrayList;

import com.capstone.consumer.messages.FlightCreatedMessage;
import com.capstone.consumer.messages.FlightLocationUpdatedMessage;
import org.locationtech.jts.geom.Geometry;
import org.webjars.NotFoundException;
import com.capstone.consumer.utils.GeoUtils;
import org.springframework.stereotype.Component;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.consumer.config.SocketIoService;
import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.shared.bindings.FlightInformationKafkaDto;
import com.capstone.consumer.messages.FlightEnteredNoFlyZoneMessage;
import com.capstone.consumer.messages.FlightPathIntersectWithNoFlyZoneMessage;


@Component
public class CollisionListenerService {
    private final List<BaseNoFlyZone> noFlyZones = new ArrayList<>();

    private final List<FlightInformation> flights = new ArrayList<>();
    private final SocketIoService socketIoService;

    public List<FlightInformation> getAllCurrentFlight() {
        return flights;
    }

    public List<BaseNoFlyZone> getAllNoFlyZones() {
        return noFlyZones;
    }

    public CollisionListenerService(SocketIoService socketIoService) {
        this.socketIoService = socketIoService;
    }

    public void trackNewNoFlyZone(BaseNoFlyZone noFlyZone) {
        noFlyZones.add(noFlyZone);

        for (var flight : flights) {
            var intersection = GeoUtils.getFlightIntersectionWithNoFlyZone(flight, noFlyZone);

            if (!intersection.isEmpty()) {
                handleFlightPathCollision(flight, noFlyZone, intersection);
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
        socketIoService.sendMessage(new FlightCreatedMessage(newFlightInformation));

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
            socketIoService.sendMessage(new FlightLocationUpdatedMessage(targetFlight.get()));

            verifyFlightPath(targetFlight.get());
        }

        if (flightInformationKafkaDto.getLocation() != null) {
            targetFlight.get().setLocation(flightInformationKafkaDto.getLocation());
            socketIoService.sendMessage(new FlightLocationUpdatedMessage(targetFlight.get()));

            verityFlightIsInNoFlyZone(targetFlight.get());
        }

        targetFlight.get()
                .setDestination(flightInformationKafkaDto.getDestination())
                .setHeading(flightInformationKafkaDto.getHeading());
    }

    public void verityFlightIsInNoFlyZone(FlightInformation flightInformation) {
        for (var noFlyZone : noFlyZones) {
            if (GeoUtils.flightIsWithinNoFlyZone(flightInformation, noFlyZone)) {
                var message = new FlightEnteredNoFlyZoneMessage(flightInformation, noFlyZone);
                socketIoService.sendMessage(message);
            }
        }
    }

    /**
     * Performs verification on the flight path to ensure that it is not colliding with any of the no fly zones that are being tracked
     *
     * @param flightInformation flight information that must be verified
     */
    private void verifyFlightPath(FlightInformation flightInformation) {
        for (var noFlyZone : noFlyZones) {
            var intersection = GeoUtils.getFlightIntersectionWithNoFlyZone(flightInformation, noFlyZone);

            if (intersection.isEmpty()) {
                continue;
            }

            if (!intersection.isEmpty()) {
                handleFlightPathCollision(flightInformation, noFlyZone, intersection);
            }
        }
    }

    private void handleFlightPathCollision(FlightInformation flightInformation, BaseNoFlyZone noFlyZone, Geometry intersection) {
        var message = new FlightPathIntersectWithNoFlyZoneMessage(flightInformation, noFlyZone);
        socketIoService.sendMessage(message);
    }
}
