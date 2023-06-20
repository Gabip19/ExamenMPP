package game.repository;

import game.domain.User;

import java.util.UUID;

public interface UserRepository extends Repository<UUID, User> {
    User findByName(String name);
}
