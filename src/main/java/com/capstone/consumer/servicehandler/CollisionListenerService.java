package com.capstone.consumer.servicehandler;

import com.capstone.consumer.config.SocketIoService;
import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.bindings.FlightInformation;
import org.springframework.stereotype.Component;

@Component
public class CollisionListenerService {
    private final SocketIoService socketIoService;

    public CollisionListenerService(SocketIoService socketIoService) {
        this.socketIoService = socketIoService;
    }

    public void handleNewNoFlyZone(BaseNoFlyZone noFlyZone) {

    }

    public void handleNewFlight(FlightInformation flightInformation) {

    }

    public void handleFlightLocationUpdated(FlightInformation flightInformation) {

    }
}
