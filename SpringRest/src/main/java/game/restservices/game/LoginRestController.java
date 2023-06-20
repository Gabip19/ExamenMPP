package game.restservices.game;

import game.domain.User;
import game.domain.validator.exceptions.LoginException;
import game.services.Services;
import jakarta.servlet.http.Cookie;
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
    public String test(@CookieValue(name = "SID", required = false) UUID sid) {
        System.out.println("MERGE!");
        return srv.faCeva("hello", sid);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
            UUID sessionId = srv.attemptLogin(user);

            Cookie sessionCookie = new Cookie("SID", sessionId.toString());
            sessionCookie.setMaxAge(24 * 60 * 60);
            sessionCookie.setHttpOnly(true);
            response.addCookie(sessionCookie);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "SID", required = false) UUID sid, HttpServletResponse response) {
        try {
            srv.logout(sid);

            Cookie sessionCookie = new Cookie("SID", "");
            sessionCookie.setMaxAge(0);
            sessionCookie.setHttpOnly(true);
            response.addCookie(sessionCookie);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
