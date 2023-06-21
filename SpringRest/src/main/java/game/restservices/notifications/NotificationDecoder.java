package game.restservices.notifications;

import com.google.gson.Gson;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class NotificationDecoder implements Decoder.Text<Notification> {
    @Override
    public Notification decode(String s) throws DecodeException {
        return new Gson().fromJson(s, Notification.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }
}
