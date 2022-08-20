package io.github.szokecsaba.vgdb.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String query,
                                    @RequestParam(required = false) String sort) {
        return gameService.getAll(page, pageSize, query, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        return gameService.get(id);
    }

    @PostMapping(value = "/upload-thumbnail", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> uploadThumbnail(@ModelAttribute MultipartFile thumbnail, @RequestParam long gameId) {
        return gameService.uploadThumbnail(thumbnail, gameId);
    }

    @PostMapping(value = "/upload-screenshots")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> uploadScreenshots(@RequestParam MultipartFile[] screenshots, @RequestParam long gameId) {
        return gameService.uploadScreenshots(screenshots, gameId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody GameDTO game) {
        return gameService.create(game);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestBody GameDTO game, @PathVariable long id) {
        return gameService.update(game, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return gameService.delete(id);
    }
}
