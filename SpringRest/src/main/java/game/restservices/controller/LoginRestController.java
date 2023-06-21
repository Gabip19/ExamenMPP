package game.restservices.controller;

import game.domain.SessionData;
import game.domain.User;
import game.domain.validator.exceptions.LoginException;
import game.services.Services;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/game/auth")
public class LoginRestController {
    private Services srv;

    @Autowired
    public void setSrv(Services srv) {
        this.srv = srv;
    }

    @GetMapping("/test")
    public String test(@RequestHeader("Session-Id") UUID sid) {
        System.out.println("MERGE!");
        return srv.faCeva("hello", sid);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
            SessionData session = srv.attemptLogin(user);

            response.addHeader("Session-Id", session.getSessionId().toString());

            return new ResponseEntity<>(session.getLoggedUser(), HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Session-Id") UUID sid) {
        try {
            srv.logout(sid);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
