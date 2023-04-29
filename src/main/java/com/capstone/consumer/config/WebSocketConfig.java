package com.capstone.consumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * This is a Configuration class that sets up the WebSocket broker and endpoints.
 * Most of the setup is really handled by Spring. Only a few things are needed to be specified
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker that allows the WebSocket configuration to function
     * @param config The config that is being configured. Handled by Spring
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * This method registers the endpoints that the front-end application connects to
     * @param registry The registry where the endpoint gets added to. Spring handles this
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Because setting up proxies and such for cross-origin scripting is kind of a pain, we allow all cross-origin requests
        registry.addEndpoint("/consumer-socket").setAllowedOriginPatterns("*").withSockJS();
    }
}
