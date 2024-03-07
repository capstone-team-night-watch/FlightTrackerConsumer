package com.capstone.consumer.config;

import lombok.extern.slf4j.Slf4j;
import com.capstone.consumer.ApplicationProperties;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
public class SocketIoConfig {
    @Bean
    public SocketIOServer getSocketIOServer(ApplicationProperties properties) {
        var config = new com.corundumstudio.socketio.Configuration();

        config.setOrigin("*");
        config.setHostname("0.0.0.0");
        config.setPort(properties.getSocketIoPort());

        return   new SocketIOServer(config);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startSocketIoServer(ApplicationReadyEvent event) {
        var context = event.getApplicationContext();

        context.getBean(SocketIOServer.class).start();
        log.info("Started Socket Io server");
    }
}
