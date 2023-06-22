package game.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "id"))

public class User extends Entity<UUID> {
    @Column(name = "username")
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
        setId(UUID.randomUUID());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
