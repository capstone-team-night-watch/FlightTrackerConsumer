package com.capstone.consumer.config;

import com.capstone.consumer.messages.SocketMessageInterface;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Component;


/*
 * Services that handle all communication from consumer to client
 */
@Component
public class MessagingService {
    private final SocketIOServer server;

    public MessagingService(SocketIOServer server) {
        this.server = server;
    }

    public void sendMessage(SocketMessageInterface socketMessage) {
        server.getRoomOperations(socketMessage.getRoom()).sendEvent(
                socketMessage.getName(),
                socketMessage
        );
    }
}
