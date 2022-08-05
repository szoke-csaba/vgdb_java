package io.github.szokecsaba.vgdb.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ResponseEntity<?> getAll() {
        List<Game> games = gameRepository.findAll();

        return ResponseEntity.ok().body(games);
    }

    public ResponseEntity<?> get(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));

        return ResponseEntity.ok().body(game);
    }

    public ResponseEntity<?> create(Game game) {
        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> update(Game gameUpdated, long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));

        gameUpdated.setId(id);
        gameUpdated.setCreated(game.getCreated());
        gameRepository.save(gameUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));

        gameRepository.delete(game);

        return ResponseEntity.ok().build();
    }
}
