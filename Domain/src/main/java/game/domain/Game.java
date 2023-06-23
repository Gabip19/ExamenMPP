package game.domain;

import jakarta.persistence.*;

import java.time.Duration;
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

    @Column(name = "score")
    private int score;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Word> configuration = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Word> playerMoves = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated
    @Column(name = "status")
    private GameStatus gameStatus;

    public Game() {
        setId(UUID.randomUUID());
    }

    public void addWord(Word coordinates) {
        configuration.add(coordinates);
        coordinates.setGame(this);
    }

    public void removeWord(Word coordinates) {
        configuration.remove(coordinates);
        coordinates.setGame(null);
    }

    public void addPlayerMove(Word coordinates) {
        playerMoves.add(coordinates);
        coordinates.setGame(this);
    }

    public void removePlayerMove(Word coordinates) {
        playerMoves.remove(coordinates);
        coordinates.setGame(null);
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

    public List<Word> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<Word> minePositions) {
        this.configuration = minePositions;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Word> getPlayerMoves() {
        return playerMoves;
    }

    public void setPlayerMoves(List<Word> playerMoves) {
        this.playerMoves = playerMoves;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        if (getEndDate() != null) {
            Duration duration = Duration.between(getStartDate(), getEndDate());
            return Math.abs(duration.toSeconds());
        }
        return -1;
    }
}
