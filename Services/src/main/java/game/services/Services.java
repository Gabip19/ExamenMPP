package game.services;

import game.domain.User;

import java.util.UUID;

public interface Services {
    String faCeva(String string, UUID sid);
    UUID attemptLogin(User user);
    void logout(UUID sid);
}
