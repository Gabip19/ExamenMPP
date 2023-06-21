package game.domain;

import java.util.UUID;

public class SessionData {
    private UUID sessionId;
    private User loggedUser;

    public SessionData(UUID sessionId, User loggedUser) {
        this.sessionId = sessionId;
        this.loggedUser = loggedUser;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
