package game.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameDTO implements Serializable {
    private UUID gameId;
    private User player;
    private int score;
    private long duration;
    private int level;
    private GameStatus gameStatus;
    private List<CoordinatesDTO> mines;
    private List<CoordinatesDTO> moves;

    public GameDTO(UUID gameId, User player, int score, long duration, int level, GameStatus gameStatus) {
        this.gameId = gameId;
        this.player = player;
        this.score = score;
        this.duration = duration;
        this.level = level;
        this.gameStatus = gameStatus;
    }

    public GameDTO(Game game) {
        gameId = game.getId();
        player = game.getPlayer();
        score = game.getScore();
        duration = game.getDuration();
        level = game.getCurrentLevel();
        gameStatus = game.getGameStatus();
        if (game.getGameStatus().equals(GameStatus.ENDED)) {
            setMinesList(game.getMinePositions());
            setMovesList(game.getPlayerMoves());
        }
    }

    public GameDTO() {
    }

    public void setMinesList(List<Coordinates> coordinates) {
        mines = new ArrayList<>(coordinates.stream().map(CoordinatesDTO::new).toList());
    }

    public void setMovesList(List<Coordinates> coordinates) {
        moves = new ArrayList<>(coordinates.stream().map(CoordinatesDTO::new).toList());
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<CoordinatesDTO> getMines() {
        return mines;
    }

    public void setMines(List<CoordinatesDTO> mines) {
        this.mines = mines;
    }

    public List<CoordinatesDTO> getMoves() {
        return moves;
    }

    public void setMoves(List<CoordinatesDTO> moves) {
        this.moves = moves;
    }
}
