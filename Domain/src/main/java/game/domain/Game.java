package game.domain;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private List<Coordinates> minePositions = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coordinates> playerMoves = new ArrayList<>();

    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated
    @Column(name = "status")
    private GameStatus gameStatus;

    @Column(name = "current_level")
    private int currentLevel = 0;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Game() {
        setId(UUID.randomUUID());
    }

    public void addMineCoordinate(Coordinates coordinates) {
        minePositions.add(coordinates);
        coordinates.setGame(this);
    }

    public void removeMineCoordinate(Coordinates coordinates) {
        minePositions.remove(coordinates);
        coordinates.setGame(null);
    }

    public void addPlayerMove(Coordinates coordinates) {
        playerMoves.add(coordinates);
        coordinates.setGame(this);
    }

    public void removePlayerMove(Coordinates coordinates) {
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

    public List<Coordinates> getMinePositions() {
        return minePositions;
    }

    public void setMinePositions(List<Coordinates> minePositions) {
        this.minePositions = minePositions;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Coordinates> getPlayerMoves() {
        return playerMoves;
    }

    public void setPlayerMoves(List<Coordinates> playerMoves) {
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

    public void nextLevel() {
        currentLevel++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return score == game.score && Objects.equals(player, game.player) && Objects.equals(minePositions, game.minePositions) && Objects.equals(playerMoves, game.playerMoves) && Objects.equals(startDate, game.startDate) && Objects.equals(endDate, game.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, score, minePositions, playerMoves, startDate, endDate);
    }
}
