package com.capstone.consumer.servicehandler;

import com.capstone.consumer.config.SocketIoService;
import com.capstone.consumer.messages.FlightEnteredNoFlyZoneMessage;
import com.capstone.consumer.messages.PathCollisionWithNoFlyZoneMessage;
import com.capstone.consumer.utils.GeoUtils;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollisionListenerService {
    private List<BaseNoFlyZone> noFlyZones = new ArrayList<>();
    private final SocketIoService socketIoService;

    public CollisionListenerService(SocketIoService socketIoService) {
        this.socketIoService = socketIoService;
    }

    public void trackNewNoFlyZone(BaseNoFlyZone noFlyZone) {
        noFlyZones.add(noFlyZone);
    }

    public void handleNoFlyZoneRemoved(BaseNoFlyZone noFlyZone) {
        noFlyZones.removeIf(x -> x.getId().equals(noFlyZone.getId()));
    }

    public void handleNewFlight(FlightInformation flightInformation) {
        verifyFlightPath(flightInformation);
        handleFlightPathUpdated(flightInformation);
    }

    public void handleFlightPathUpdated(FlightInformation flightInformation) {
        verifyFlightPath(flightInformation);
    }

    public void handleFlightLocationUpdated(FlightInformation flightInformation) {
        for (var noFlyZone : noFlyZones) {
            if (GeoUtils.flightIsWithinNoFlyZone(flightInformation, noFlyZone)) {
                var message = new FlightEnteredNoFlyZoneMessage(flightInformation, noFlyZone);
                socketIoService.sendMessage(message);
            }
        }
    }

    private void verifyFlightPath(FlightInformation flightInformation) {
        for (var noFlyZone : noFlyZones) {
            if (GeoUtils.flightIsIntersectingWithNoFlyZone(flightInformation, noFlyZone)) {
                var message = new PathCollisionWithNoFlyZoneMessage(flightInformation, noFlyZone);
                socketIoService.sendMessage(message);
            }
        }
    }
}
