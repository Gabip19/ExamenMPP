package game.restservices.controller;

import game.domain.Coordinates;
import game.domain.Game;
import game.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/game/games")
public class GameController {
    private Services srv;

    @Autowired
    public void setSrv(Services srv) {
        this.srv = srv;
    }

    @PostMapping
    public ResponseEntity<?> startGame(@RequestBody Coordinates coordinates, @CookieValue(name = "SID") UUID sid) {
        Game game = srv.startGame(coordinates, sid);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
