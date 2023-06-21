package game.restservices.notifications;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.GameDTO;
import game.services.NotificationSystem;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class WebSocketsNotificationSystem implements NotificationSystem {
    private static final Map<UUID, Session> webSocketConnections = new HashMap<>();

    public void addWebSocketConnection(UUID userId, Session session) {
        webSocketConnections.put(userId, session);
    }

    public void removeWebSocketConnection(UUID userId, Session session) throws IOException {
        if (webSocketConnections.get(userId).equals(session)) {
            webSocketConnections.remove(userId);
            session.close();
        }
    }

    @Override
    public void notifyGameStarted(Game game) {
        Notification notification = new Notification(
            NotificationType.GAME_STARTED,
            new GameDTO(game)
        );
        try {
            webSocketConnections.get(game.getPlayerOneId()).getBasicRemote().sendObject(notification);
            webSocketConnections.get(game.getPlayerTwoId()).getBasicRemote().sendObject(notification);
        } catch (EncodeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyGameEnded(Game game) {
        Notification notification = new Notification(
            NotificationType.GAME_ENDED,
            new GameDTO(game)
        );
        try {
            webSocketConnections.get(game.getPlayerOneId()).getBasicRemote().sendObject(notification);
            webSocketConnections.get(game.getPlayerTwoId()).getBasicRemote().sendObject(notification);
        } catch (EncodeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyNewMove(Game game, Coordinates coordinates) {
        Notification notification = new Notification(
            NotificationType.NEW_MOVE,
            coordinates
        );
        try {
            webSocketConnections.get(game.getActivePlayerId()).getBasicRemote().sendObject(notification);
        } catch (EncodeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
