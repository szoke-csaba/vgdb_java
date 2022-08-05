package io.github.szokecsaba.vgdb.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> get(@PathVariable long id) {
        return gameService.get(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Game game) {
        return gameService.create(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Game game, @PathVariable long id) {
        return gameService.update(game, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return gameService.delete(id);
    }
}
