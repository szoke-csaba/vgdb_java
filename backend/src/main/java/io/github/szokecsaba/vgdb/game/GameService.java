package io.github.szokecsaba.vgdb.game;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final PagingService pagingService;

    @Autowired
    public GameService(GameRepository gameRepository, PagingService pagingService) {
        this.gameRepository = gameRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Game> games = gameRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(games, "games");

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
