package game.repository;

import game.domain.Game;
import game.domain.GameStatus;
import game.domain.User;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends Repository<UUID, Game> {
    List<Game> findByGameStatus(GameStatus gameStatus);
    List<Game> findByUserAndStatus(User user, GameStatus gameStatus);
}
