package game.domain;

import java.io.Serial;
import java.io.Serializable;

public abstract class Entity<TID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 57432978279372L;
    private TID id;

    public TID getId() {
        return id;
    }

    public void setId(TID id) {
        this.id = id;
    }
}
