package game.restservices.notifications;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.GameDTO;
import game.domain.User;
import game.services.NotificationSystem;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

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
    public void notifyGameStarted(User user, List<Game> gameList) {
        Notification notification = new Notification(
            NotificationType.GAME_STARTED,
            new ArrayList<>(gameList.stream().map((game) -> new GameDTO(game)).toList())
        );
        try {
            webSocketConnections.get(user.getId()).getBasicRemote().sendObject(notification);
        } catch (EncodeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyTopUpdate(List<Game> gameList) {
        Notification notification = new Notification(
            NotificationType.GAME_ENDED,
            new ArrayList<>(gameList.stream().map((game) -> new GameDTO(game)).toList())
        );
        System.out.println("Sending notification -----");
        try {
            for (Session session : webSocketConnections.values()) {
                session.getBasicRemote().sendObject(notification);
            }
        } catch (EncodeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyNewMove(Game game, Coordinates coordinates) {
//        System.out.println("Sending new MOVE to " + game.getId());
//        Notification notification = new Notification(
//            NotificationType.NEW_MOVE,
//            coordinates
//        );
//        try {
//            webSocketConnections.get(game.getId()).getBasicRemote().sendObject(notification);
//        } catch (EncodeException | IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
