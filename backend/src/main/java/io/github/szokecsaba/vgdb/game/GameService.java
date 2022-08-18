package io.github.szokecsaba.vgdb.game;

import io.github.szokecsaba.vgdb.util.FileHandlerUtil;
import io.github.szokecsaba.vgdb.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class GameService {
    @Value("${games.thumbnail.directory}")
    private String THUMBNAIL_DIRECTORY;
    private static final String GAME_NOT_FOUND = "Game not found with id: ";

    private final GameRepository gameRepository;
    private final PagingUtil pagingUtil;
    private final FileHandlerUtil fileHandlerUtil;
    private final GameMapper gameMapper;

    @Autowired
    public GameService(GameRepository gameRepository, PagingUtil pagingUtil, FileHandlerUtil fileHandlerUtil, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.pagingUtil = pagingUtil;
        this.fileHandlerUtil = fileHandlerUtil;
        this.gameMapper = gameMapper;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Game> games = gameRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(games, "games");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + id));

        return ResponseEntity.ok().body(game);
    }

    public ResponseEntity<?> create(GameDTO gameDTO) throws IOException {
        MultipartFile thumbnailFile = gameDTO.getThumbnailFile();
        String fileName;

        if (thumbnailFile != null) {
            fileHandlerUtil.setData(thumbnailFile, THUMBNAIL_DIRECTORY);
            fileHandlerUtil.saveFile();
            fileName = fileHandlerUtil.getFileName();
            gameDTO.setThumbnail(fileName);
        }

        Game game = gameMapper.toGame(gameDTO);
        gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(GameDTO gameDTO, long id) throws IOException {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + id));

        MultipartFile thumbnailFile = gameDTO.getThumbnailFile();
        String fileName;

        if (thumbnailFile != null) {
            fileHandlerUtil.setData(thumbnailFile, THUMBNAIL_DIRECTORY);
            fileHandlerUtil.saveFile();
            fileName = fileHandlerUtil.getFileName();
            gameDTO.setThumbnail(fileName);
        }

        game = gameMapper.toGame(gameDTO, game);
        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + id));

        if (game.getThumbnail() != null) {
            fileHandlerUtil.delete(THUMBNAIL_DIRECTORY + game.getThumbnail());
        }

        gameRepository.delete(game);

        return ResponseEntity.ok().build();
    }
}
