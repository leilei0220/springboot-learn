package com.leilei.config;

import com.leilei.service.LocationService;
import com.leilei.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lei
 * @create 2023-05-05 10:58
 * @desc
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig {


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     * webSocket无法注入spring bean 故此需要执行初始化操作
     *
     * @param locationService
     * @return void
     * @author lei
     * @date 2023-05-05 11:44:16
     */
    @Autowired
    public void setWebSocketLocationService(LocationService locationService) {
        WebSocketService.LOCATION_SERVICE = locationService;
    }


}
