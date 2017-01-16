package com.learn.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * ->AbstractWebSocketMessageBrokerConfigurer class allows us to configure some key settings
 * for web socket broker.
 * -> configureMessageBroker method is not abstract but it allows us enable two types of broker
 * 1. simple memory based broker
 * 2. stampBrokerRelay which you can use to register external brokers e.g. RabbitMQ where you can
 * persist messages as well.
 */


@Configuration
@EnableWebSocketMessageBroker
/**
 * Above annotation configures a centralized broker in our app which routes messages from client to server and vice
 * versa.
 */
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // target to the broker. Means messages from server out to the client
        registry.enableSimpleBroker("/topic");
        //prefix for messages that come from client to server side components.
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    /*
    -> Spring MVC leverages Stomp as a message format of choice on the top of Websocket very
    lightweight protocol. Websocket protocol is actually a socket get opened up between a web layer
    and backend which is full duplex channel so traffic can travel in either direction. But websocket protocol
    doesn't specify anything about the data that travels so its left upto the developers to put that in.
    So the recommendation is to use some form of sub protocols and Stomp is one those populer sub protocols.
     */
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*->register endpoints which is path for all the clients that need to be registered
            for these websocket messages from server.
          ->Spring provides out of box support with SockJs, SockJS is library which provides
          gracefull fallback for browsers that do not support this websocket protocol yet. So for
          these browsers it will make them behave like they have websocket protocol support.
        */
        registry.addEndpoint("/imageMessages").withSockJS();
    }
}
