//package com.example.study.global.webSoKet;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/queue", "/topic"); //구독경로
//        registry.setApplicationDestinationPrefixes("/app"); //데이터 가공 경로 (결국 /topic으로 연결됨)
//    }
//
//    /**
//     * 참고로 일종의 관습으로, queue라는 prefix 는 메시지가 1:1로 송신될 때,
//     * 그리고 topic 이라는 prefix는 메시지가 1:다 로
//     * 여러명에게 송신될 때 주로 사용
//     */
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/gs-guide-websocket")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//}
//
//
//    /** 웹소켓만 사용했을때 사용
//        @Override
//        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//            // endpoint 설정 : /api/v1/chat/{postId}
//            // 이를 통해서 ws://localhost:9090/ws/chat 으로 요청이 들어오면 websocket 통신을 진행한다.
//            // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
//            registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//        }
//
//    //웹소켓 과 STOMP를 활요한 실시간 채팅 구현
//    //STOMP를 사용하면 WebSoket Handler를 작성할 필요가 없다.
//     **/
//
//
