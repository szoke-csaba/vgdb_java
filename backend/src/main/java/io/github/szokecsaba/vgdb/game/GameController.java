package io.github.szokecsaba.vgdb.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return gameService.getAll();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> get(@PathVariable long id) {
        return gameService.get(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> create(@Valid @RequestBody Game game) {
        return gameService.create(game);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> update(@Valid @RequestBody Game game, @PathVariable long id) {
        return gameService.update(game, id);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return gameService.delete(id);
    }
}
