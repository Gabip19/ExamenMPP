package game.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "games")
@AttributeOverride(name = "id", column = @Column(name = "id"))

public class Game extends Entity<UUID> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private User player;

    @Column(name = "total_sum")
    private int totalSum;

    @Column(name = "current_position")
    private int currentPosition;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coordinates> configuration = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Enumerated
    @Column(name = "status")
    private GameStatus gameStatus;

    @Column(name = "generation_number")
    private int generationNumber;

    public Game() {
        setId(UUID.randomUUID());
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public void addConfigurationCoordinate(Coordinates coordinates) {
        configuration.add(coordinates);
        coordinates.setGame(this);
    }

    public void removeConfigurationCoordinate(Coordinates coordinates) {
        configuration.remove(coordinates);
        coordinates.setGame(null);
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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public List<Coordinates> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<Coordinates> configuration) {
        this.configuration = configuration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
