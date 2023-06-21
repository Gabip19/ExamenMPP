package game.restservices.notifications;

import game.domain.GameDTO;

import java.io.Serializable;

public class Notification implements Serializable {
    private NotificationType notificationType;
    private Object data;

    public Notification(NotificationType notificationType, Object data) {
        this.notificationType = notificationType;
        this.data = data;
    }

    public Notification() {
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
