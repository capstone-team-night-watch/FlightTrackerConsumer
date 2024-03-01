package com.capstone.consumer.config;

import com.capstone.consumer.bindings.CircularNoFlyZone;
import com.capstone.consumer.bindings.FlightInformation;
import com.capstone.consumer.bindings.BaseNoFlyZone;
import com.capstone.consumer.bindings.PolygonNoFlyZone;
import com.capstone.consumer.enums.NoFlyZoneType;
import com.capstone.consumer.messages.FlightIntersectWithNoFlyZoneMessage;
import com.capstone.consumer.messages.FlightLocationUpdatedMessage;
import com.capstone.consumer.messages.NoFlyZoneCreatedMessage;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class SocketIoService {
    private final SocketIOServer server;
    private static final String NO_FLY_ZONE_ROOM = "no-fly-zone";

    public SocketIoService(SocketIOServer server) {
        this.server = server;

        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());

        configureHealthCheck();
    }

    /**
     * Configure the health check to communicate socket status with clients
     */
    public void configureHealthCheck(){
        var timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                server.getBroadcastOperations().sendEvent("broadcast-health-check", "Server is still alive!");
                log.info("Running periodic health check broadcast!");
            }
        },  1000,  1000);

        server.addEventListener("health-check", String.class, (client, data, ackSender) -> {
            client.sendEvent("health-check", "Server is still alive!");
            log.info("Received health check from client: {}", data);
        });
    }

    /**
     * Notify the client that a flight has intersected with a no-fly zone
     *
     * @param flightId          the flight that intersected with the no-fly zone
     * @param flightInformation the flight information
     */
    public void notifyFlightLocationUpdated(String flightId, FlightInformation flightInformation) {
        server.getRoomOperations(flightId).sendEvent(
                "flight-location-updated",
                new FlightLocationUpdatedMessage()
                        .setFlightId(flightId)
                        .setLocation(flightInformation.getLocation())
                        .setGroundSpeed(flightInformation.getGroundSpeed())
                        .setHeading(flightInformation.getHeading())
        );
    }

    /**
     * Notify the client that a new no-fly zone has been created
     *
     * @param noFlyZone information about the no-fly-zone that has just been created
     */
    public void notifyNoFlyZoneCreated(BaseNoFlyZone noFlyZone) {
        server.getRoomOperations(NO_FLY_ZONE_ROOM).sendEvent(
                "circular-no-fly-zone-created",
                new NoFlyZoneCreatedMessage()
                        .setNoFlyZoneId(noFlyZone.getId())
                        .setNoFlyZone(noFlyZone)
                        .setNoFlyZoneMessage("A new more place where you don't get to fly")
        );
    }

    /**
     * Notifies the client that a no-fly zone has been deleted
     *
     * @param noFlyZoneId the id of the no-fly-zone that has been deleted
     */
    public void notifyNoFlyZoneDeleted(String noFlyZoneId) {
        server.getRoomOperations(NO_FLY_ZONE_ROOM).sendEvent(
                "no-fly-zone-deleted",
                noFlyZoneId
        );
    }

    /**
     * Notifies the client that a flight intersect with a no-fly zone
     *
     * @param flightInformation information about the flight that is intersecting
     * @param noFlyZone information about the flight zone that the flight is or is about to intersect with
     */
    public void notifyFlightIntersectionWithNoFlightZone(FlightInformation flightInformation, BaseNoFlyZone noFlyZone) {
        server.getRoomOperations(flightInformation.getFlightId()).sendEvent(
                "flight-intersect-with-no-fly-zone",
                new FlightIntersectWithNoFlyZoneMessage()
                        .setFlightInformation(flightInformation)
                        .setNoFlyZone(noFlyZone)
        );
    }

    private DataListener<String> onChatReceived() {
        return (senderClient, message, ackSender) -> {
            log.info("Received message from client: {}", message);
            senderClient.sendEvent("received_message", message);
        };
    }

    private ConnectListener onConnected() {
        return client -> log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());

    }

    private DisconnectListener onDisconnected() {
        return client -> log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    }
}
