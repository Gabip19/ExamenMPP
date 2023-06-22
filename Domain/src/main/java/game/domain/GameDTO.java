package game.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameDTO implements Serializable {
    private UUID gameId;
    private User player;
    private int totalSum;
    private LocalDateTime startTime;
    private int position;
    private GameStatus gameStatus;
    private int generationNumber;
    private List<CoordinatesDTO> configuration;

    public GameDTO(Game game) {
        gameId = game.getId();
        player = game.getPlayer();
        totalSum = game.getTotalSum();
        startTime = game.getStartDate();
        position = game.getCurrentPosition();
        gameStatus = game.getGameStatus();
        generationNumber = game.getGenerationNumber();
        configuration = new ArrayList<>(game.getConfiguration().stream().map(CoordinatesDTO::new).toList());
    }

    public GameDTO() {
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

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public List<CoordinatesDTO> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<CoordinatesDTO> configuration) {
        this.configuration = configuration;
    }
}
