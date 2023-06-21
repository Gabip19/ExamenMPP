package game.restservices.notifications;

import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class NotificationEncoder implements Encoder.Text<Notification> {
    private static final Gson gson = new Gson();

    @Override
    public String encode(Notification notification) throws EncodeException {
        return gson.toJson(notification);
    }
}
