package io.github.szokecsaba.vgdb.game;

import io.github.szokecsaba.vgdb.screenshot.Screenshot;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GameService {
    @Value("${games.thumbnail.directory}")
    private String THUMBNAIL_DIRECTORY;
    @Value("${games.screenshot.directory}")
    private String SCREENSHOTS_DIRECTORY;
    private static final String GAME_NOT_FOUND = "Game not found with id: ";

    private final GameRepository gameRepository;
    private final PagingUtil pagingUtil;
    private final FileHandlerUtil fileHandlerUtil;
    private final GameMapper gameMapper;

    @Autowired
    public GameService(GameRepository gameRepository, PagingUtil pagingUtil,
                       FileHandlerUtil fileHandlerUtil, GameMapper gameMapper) {
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

    public ResponseEntity<?> create(GameDTO gameDTO) {
        Game game = gameMapper.toGame(gameDTO);

        gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).body(game.getId());
    }

    public ResponseEntity<?> update(GameDTO gameDTO, long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + id));

        game = gameMapper.toGame(gameDTO, game);
        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + id));

        deleteThumbnail(game);
        deleteScreenshots(game);

        gameRepository.delete(game);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> uploadThumbnail(MultipartFile thumbnail, long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        deleteThumbnail(game);
        String fileName = saveFileThenGetItsName(thumbnail, THUMBNAIL_DIRECTORY);
        game.setThumbnail(fileName);
        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> uploadScreenshots(MultipartFile[] screenshots, long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        deleteScreenshots(game);
        game.getScreenshots().clear();

        Set<Screenshot> screenshotsSet = new HashSet<>();

        for (MultipartFile screenshotFile: screenshots) {
            String fileName = saveFileThenGetItsName(screenshotFile, SCREENSHOTS_DIRECTORY);
            screenshotsSet.add(new Screenshot(0, fileName));
        }

        game.getScreenshots().addAll(screenshotsSet);
        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    private String saveFileThenGetItsName(MultipartFile file, String directory) {
        fileHandlerUtil.setData(file, directory);

        try {
            fileHandlerUtil.saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileHandlerUtil.getFileName();
    }

    private void deleteThumbnail(Game game) {
        if (game.getThumbnail() != null) {
            fileHandlerUtil.delete(THUMBNAIL_DIRECTORY + game.getThumbnail());
        }
    }

    private void deleteScreenshots(Game game) {
        if (!game.getScreenshots().isEmpty()) {
            for (Screenshot screenshot: game.getScreenshots()) {
                fileHandlerUtil.delete(SCREENSHOTS_DIRECTORY + screenshot.getName());
            }
        }
    }
}
