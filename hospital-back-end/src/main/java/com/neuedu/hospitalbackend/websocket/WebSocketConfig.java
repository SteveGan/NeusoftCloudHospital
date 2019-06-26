package com.neuedu.hospitalbackend.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {


    /**
     * 配置信息代理
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        //name of the broker
        //enable a simple memory-based message broker to carry the greeting
        // messages back to the client on destinations prefixed with "/topic".
        config.enableSimpleBroker("/topic","/queue");

//        //It also designates the "/app" prefix for messages that are bound for
//        // @MessageMapping-annotated methods. This prefix will be used to define all
//        // the message mappings; for example, "/app/hello" is the endpoint that the
//        // GreetingController.greeting() method is mapped to handle.
//        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
//        config.setApplicationDestinationPrefixes("/app");

    }

    // The registerStompEndpoints() method registers the "/gs-guide-websocket" endpoint,
    // enabling SockJS fallback options so that alternate transports may be used
    // if WebSocket is not available. The SockJS client will attempt to connect
    // to "/gs-guide-websocket" and use the best transport available (websocket,
    // xhr-streaming, xhr-polling, etc).
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/webSocketServer
        // 来和服务器的WebSocket连接
        registry.addEndpoint("/webSocketServer").setAllowedOrigins("*").withSockJS();
    }

}


