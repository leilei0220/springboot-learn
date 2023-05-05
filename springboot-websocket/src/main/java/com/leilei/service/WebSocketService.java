package com.leilei.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author lei
 * @create 2023-05-05 11:00
 * @desc  Spring Boot默认将每个客户端连接视为独立的会话，并为每个客户端连接创建一个新的WebSocketHandler实例
 * (类似于原型模式 @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE))
 *
 *
 * @ServerEndpoint：使用此注解可以将一个 Java 类标记为 WebSocket 服务器端点。它接受一个 value 参数，用于指定 URI。
 * 当客户端连接到该 URI 时，将会使用标注了 @ServerEndpoint 注解的类进行处理
 **/
@Component
@Log4j2
@ServerEndpoint("/lei/{userId}")
public class WebSocketService {


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String userId;

    /**
     * 无法注入 使用自动注入特性为其静态赋值，详见WebSocketConfig
     */
    public static LocationService LOCATION_SERVICE;


    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
     */
    private static final CopyOnWriteArraySet<WebSocketService> SOCKET_SET = new CopyOnWriteArraySet<>();

   /**
    * @OnOpen：使用此注解可以指定一个方法，该方法在 WebSocket 连接建立时调用。它可以接受一个 javax.websocket.Session 参数，用于表示与客户端的连接
    *
    */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        SOCKET_SET.add(this);
        sendMessage(session, String.format("用户：%s连接成功,当前在线用户数量：%d", userId, getOnlineCount()));
        sendMessage(session, String.format("获取到最新定位信息:%s", LOCATION_SERVICE.getLastLocationByUser(userId)));
    }

    /**
     * 连接关闭调用的方法
     * @OnClose：使用此注解可以指定一个方法，该方法在 WebSocket 连接关闭时调用。它可以接受一个 javax.websocket.Session 参数，用于表示与客户端的连接
     */
    @OnClose
    public void onClose(Session session) {
        log.info("用户:{}关闭连接", userId);
        SOCKET_SET.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     * @OnMessage：使用此注解可以指定一个方法，该方法在 WebSocket 接收到消息时调用。它可以接受一个 javax.websocket.Session 参数，用于表示与客户端的连接，
     * 以及一个 String 参数，用于表示收到的消息内容
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到客户端消息：{}{}", message, session);
    }

    /**
     * @OnError：使用此注解可以指定一个方法，该方法在 WebSocket 发生错误时调用。它可以接受一个 javax.websocket.Session 参数，用于表示与客户端的连接，以及一个 Throwable 参数，用于表示发生的错误
     *  错误回调函数
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }



    /**
     * 获取当前用户数
     * @return
     */
    public static int getOnlineCount() {
        return SOCKET_SET.size();
    }


    /**
     * 服务器向客户端推送消息
     *
     * @param session
     * @param message
     * @return void
     * @author lei
     * @date 2023-05-05 11:34:58
     */
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("发消息异常：" + e);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebSocketService)) {
            return false;
        }
        WebSocketService that = (WebSocketService) o;
        return Objects.equals(session, that.session) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, userId);
    }
}
