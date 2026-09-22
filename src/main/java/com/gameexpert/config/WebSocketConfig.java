package com.gameexpert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.gameexpert.ws.GameWebSocketHandler;
import com.gameexpert.ws.NicknameHandshakeInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@EnableScheduling
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameWebSocketHandler gameWebSocketHandler;
    private final NicknameHandshakeInterceptor nicknameInterceptor;
    private final EngineProperties properties;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // TODO Lv 8: 제공된 인터셉터를 핸들러 등록에 연결합니다.
        registry.addHandler(gameWebSocketHandler, "/ws/worlds/{worldId}")
                .setAllowedOriginPatterns(properties.wsAllowedOrigins().toArray(String[]::new))
                .addInterceptors(nicknameInterceptor);
    }

    // 클라이언트가 15초마다 ping을 보내므로, 그 두 배가 넘는 기간 동안 아무 메시지도 없으면
    // 브라우저가 비정상 종료되어 종료 프레임을 못 보낸 죽은 연결로 보고 서버가 직접 끊습니다.
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxSessionIdleTimeout(35_000L);
        return container;
    }
}
