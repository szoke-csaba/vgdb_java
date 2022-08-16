package io.github.szokecsaba.vgdb.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameService {
    private final int DEFAULT_PAGE_SIZE = 12;

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        page = page != null ? page : 0;
        pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int currentPage = Math.max(0, page - 1);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Game> games = gameRepository.findAll(pageable);

        Map<String, Integer> paging = Map.of(
                "pages", games.getTotalPages(),
                "currentPage", currentPage + 1
        );

        Map<String, Object> response = Map.of(
                "games", games.toList(),
                "paging", paging
        );

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));

        return ResponseEntity.ok().body(game);
    }

    public ResponseEntity<?> create(Game game) {
        gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
