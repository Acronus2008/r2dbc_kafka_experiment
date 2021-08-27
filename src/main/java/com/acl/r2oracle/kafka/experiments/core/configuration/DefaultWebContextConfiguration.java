package com.acl.r2oracle.kafka.experiments.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DefaultWebContextConfiguration {
    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory() {
            @Override
            public WebServer getWebServer(HttpHandler httpHandler) {
                Map<String, HttpHandler> handlerMap = new HashMap<>();
                handlerMap.put(path, httpHandler);
                return super.getWebServer(new ContextPathCompositeHandler(handlerMap));
            }
        };
        webServerFactory.addServerCustomizers(this.portCustomizer());
        return webServerFactory;
    }

    public NettyServerCustomizer portCustomizer() {
        return httpServer -> httpServer.port(port);
    }

    private @Value("${server.servlet.context-path}")
    String path;
    private @Value("${server.port}")
    int port;
}
