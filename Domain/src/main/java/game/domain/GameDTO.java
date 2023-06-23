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
    private GameStatus gameStatus;
    private List<WordDTO> configuration;
    private List<PlayerMoveDTO> moves;

    public GameDTO(UUID gameId, User player, int score, long duration, GameStatus gameStatus) {
        this.gameId = gameId;
        this.player = player;
        this.score = score;
        this.duration = duration;
        this.gameStatus = gameStatus;
    }

    public GameDTO(Game game) {
        gameId = game.getId();
        player = game.getPlayer();
        score = game.getScore();
        duration = game.getDuration();
        gameStatus = game.getGameStatus();
        moves = new ArrayList<>(game.getPlayerMoves().stream().map(PlayerMoveDTO::new).toList());
        if (game.getGameStatus().equals(GameStatus.ENDED)) {
            configuration = new ArrayList<>(game.getConfiguration().stream().map(WordDTO::new).toList());
        }
    }

    public GameDTO() {
    }

    public void setMinesList(List<Word> coordinates) {
        configuration = new ArrayList<>(coordinates.stream().map(WordDTO::new).toList());
    }

    public void setMovesList(List<PlayerMove> coordinates) {
        moves = new ArrayList<>(coordinates.stream().map(PlayerMoveDTO::new).toList());
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

    public void setScore(int score) {
        this.score = score;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<WordDTO> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<WordDTO> configuration) {
        this.configuration = configuration;
    }

    public List<PlayerMoveDTO> getMoves() {
        return moves;
    }

    public void setMoves(List<PlayerMoveDTO> moves) {
        this.moves = moves;
    }
}
