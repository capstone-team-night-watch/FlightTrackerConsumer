package com.capstone.consumer.config;

import com.capstone.consumer.enums.Messages;
import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.servicehandler.CollisionListenerService;
import com.capstone.shared.bindings.BaseNoFlyZone;
import lombok.extern.slf4j.Slf4j;
import com.capstone.consumer.messages.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.capstone.consumer.servicehandler.NoFlyZoneService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import java.util.*;

@Slf4j
@Component
public class SocketIoService {
    private final SocketIOServer server;

    private final NoFlyZoneService noFlyZoneService;

    private final CollisionListenerService collisionListenerService;

    public SocketIoService(
            SocketIOServer server,
            NoFlyZoneService noFlyZoneService,
            CollisionListenerService collisionListenerService) {
        this.server = server;
        this.noFlyZoneService = noFlyZoneService;
        this.collisionListenerService = collisionListenerService;

        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());

        configureHealthCheck();
    }

    /**
     * Configure the health check to communicate socket status with clients
     */
    public void configureHealthCheck() {
        var timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                server.getBroadcastOperations().sendEvent("broadcast-health-check", "Server is still alive!");
                log.info("Running periodic health check broadcast!");
            }
        }, 1000, 1000);

        server.addEventListener("health-check", String.class, (client, data, ackSender) -> {
            client.sendEvent("health-check", "Server is still alive!");
            log.info("Received health check from client: {}", data);
        });

        server.addEventListener("join-rooms", JoinRoomPayload.class, handleJoinRoomRequest());
        server.addEventListener("leave-rooms", ExitRoomPayload.class, handleExitRoomRequest());
    }

    /**
     * Notify the client that a new no-fly zone has been created
     *
     * @param noFlyZone information about the no-fly-zone that has just been created
     */
    public void notifyNoFlyZoneCreated(BaseNoFlyZone noFlyZone) {
        server.getRoomOperations(Rooms.NO_FLY_ZONE_ROOM).sendEvent(
                Messages.NO_FLY_ZONE_CREATED,
                new NoFlyZoneCreatedMessage()
                        .setNoFlyZoneId(noFlyZone.getId())
                        .setNoFlyZone(noFlyZone)
                        .setType(noFlyZone.getType())
                        .setNoFlyZoneMessage("A new more place where you don't get to fly")
        );

        noFlyZoneService.storeNoFlyZone(noFlyZone);
    }


    private ConnectListener onConnected() {
        return client -> log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());

    }

    private DisconnectListener onDisconnected() {
        return client -> log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    }

    /**
     * Joins no-fly-zone room and notifies the client of the already existing no-fly-zone
     *
     * @param client client that should join no-fly-zone
     */
    private void joinNoFlyZoneRoom(SocketIOClient client) {
        client.joinRoom(Rooms.NO_FLY_ZONE_ROOM);

        var currentNoFlyZone = noFlyZoneService.getNoFlyZones();

        for (var baseNoFlyZone : currentNoFlyZone) {
            client.sendEvent("active-no-fly-zone", baseNoFlyZone);
        }
    }

    /**
     * Join flight lobby and initialize client with flight lobby information
     *
     * @param client client that should join the new lobby
     */
    private void joinFlightLobby(SocketIOClient client) {
        client.joinRoom(Rooms.FLIGHT_INFORMATION_LOBBY);

        var activeFlights = collisionListenerService.getAllCurrentFlight();

        for (var activeFlight : activeFlights) {
            client.sendEvent("active-flight", activeFlight);
        }
    }


    /**
     * Handle the request from the client to join a room
     */
    private DataListener<JoinRoomPayload> handleJoinRoomRequest() {
        return (client, data, ackSender) -> {
            for (String room : data.getRooms()) {
                if (room.equals(Rooms.FLIGHT_INFORMATION_LOBBY)) {
                    joinFlightLobby(client);
                } else if (room.equals(Rooms.NO_FLY_ZONE_ROOM)) {
                    joinNoFlyZoneRoom(client);
                } else client.joinRoom(room);
            }

            log.info("Client[{}] - Joined rooms: {}", client.getSessionId().toString(), data.getRooms());

            var ack = new OperationAck()
                    .setCode(HttpStatus.OK)
                    .setMessage("You have been added to rooms " + data.getRooms());

            ackSender.sendAckData(ack);
        };
    }

    /**
     * Handle the request from the client to leave a room
     */
    private DataListener<ExitRoomPayload> handleExitRoomRequest() {
        return (client, data, ackSender) -> {
            for (String room : data.getRooms()) {
                client.leaveRoom(room);
            }

            log.info("Client[{}] - Left rooms: {}", client.getSessionId().toString(), data.getRooms());

            var ack = new OperationAck()
                    .setCode(HttpStatus.OK)
                    .setMessage("You have been removed from rooms" + data.getRooms());

            ackSender.sendAckData(ack);
        };
    }
}
