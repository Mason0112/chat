package com.mason.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //註冊STOMP協議 會利用下面的/ws連線到websocket
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //註冊一個一個simpleBroker 負責將消息從發送者路由到一個或多個接收者。
        config.enableSimpleBroker("/topic");
        //發送訊息給伺服器前要有的前綴
        config.setApplicationDestinationPrefixes("/app");
    }
}