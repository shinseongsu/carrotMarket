package com.carret.market.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
            .setAllowedOriginPatterns("*")
            .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
            .setRelayHost("localhost")
            .setVirtualHost("/")
            .setRelayPort(61613)
            .setClientLogin("admin")
            .setClientPasscode("admin")
            .setSystemLogin("admin")
            .setSystemPasscode("admin");
    }
}
