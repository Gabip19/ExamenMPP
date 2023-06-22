package game.restservices.controller;

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
@RequestMapping("/minesweeper/users")
public class UserController {
    private Services srv;

    @Autowired
    public void setSrv(Services srv) {
        this.srv = srv;
    }

    @GetMapping("/{userId}/games")
    public ResponseEntity<?> getGamesForUser(@PathVariable UUID userId) {
        List<Game> games = srv.getAllFinishedGamesForUser(userId);
        if (games != null) {
            List<GameDTO> gameDTOS = new ArrayList<>(games.stream().map(GameDTO::new).toList());
            return new ResponseEntity<>(gameDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
