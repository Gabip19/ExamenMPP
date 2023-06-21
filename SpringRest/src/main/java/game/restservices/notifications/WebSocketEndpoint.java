package game.restservices.notifications;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@ServerEndpoint(
    value = "/websocket/{userId}",
    decoders = NotificationDecoder.class,
    encoders = NotificationEncoder.class
)
public class WebSocketEndpoint {
    private static final WebSocketsNotificationSystem notificationSystem = new WebSocketsNotificationSystem();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        System.out.println("CEVA CEVA ENDPOINT");
        notificationSystem.addWebSocketConnection(UUID.fromString(userId), session);
    }

    @OnMessage
    public void onMessage(Session session, Notification message) throws IOException {
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) throws IOException {
        notificationSystem.removeWebSocketConnection(UUID.fromString(userId), session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
