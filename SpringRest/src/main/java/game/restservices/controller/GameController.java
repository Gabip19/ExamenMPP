package game.restservices.controller;

import game.domain.Coordinates;
import game.domain.Game;
import game.domain.GameDTO;
import game.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/random-moves/games")
public class GameController {
    private Services srv;

    @Autowired
    public void setSrv(Services srv) {
        this.srv = srv;
    }

    @PostMapping
    public ResponseEntity<?> startGame(@RequestHeader("Session-Id") UUID sid) {
        if (srv.hasValidSession(sid)) {
            Game game = srv.startGame(sid);
            GameDTO gameDTO = new GameDTO(game);
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<?> makeMove(
            @PathVariable UUID gameId,
            @RequestBody Integer generatedNumber,
            @RequestHeader("Session-Id") UUID sid
    ) {
        if (srv.hasValidSession(sid)) {
            Game game = srv.makeMove(gameId, generatedNumber, sid);
            GameDTO gameDTO = new GameDTO(game);
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Game> games = srv.getAllGames();
        List<GameDTO> gameDTOS = new ArrayList<>();
        for (Game game : games) {
            gameDTOS.add(new GameDTO(game));
        }
        return new ResponseEntity<>(gameDTOS, HttpStatus.OK);
    }
}
