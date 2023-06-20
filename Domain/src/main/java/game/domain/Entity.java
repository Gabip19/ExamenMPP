package game.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class Entity<TID extends Serializable> implements Serializable {
    @Id
    @Column(name = "id")
    private TID id;

    public TID getId() {
        return id;
    }

    public void setId(TID id) {
        this.id = id;
    }
}
