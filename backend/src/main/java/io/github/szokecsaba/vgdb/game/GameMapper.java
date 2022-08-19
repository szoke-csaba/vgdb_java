package io.github.szokecsaba.vgdb.game;

import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public Game toGame(GameDTO gameDTO) {
        Game game = Game.builder()
                .title(gameDTO.getTitle())
                .rawgId(gameDTO.getRawgId())
                .thumbnail(gameDTO.getThumbnail())
                .tags(gameDTO.getTags())
                .genres(gameDTO.getGenres())
                .developers(gameDTO.getDevelopers())
                .publishers(gameDTO.getPublishers())
                .platforms(gameDTO.getPlatforms())
                .build();

        if (gameDTO.getReleaseDate().isPresent()) {
            game.setReleaseDate(gameDTO.getReleaseDate().get());
        }

        return game;
    }

    public Game toGame(GameDTO gameDTO, Game game) {
        game.setTitle(gameDTO.getTitle());
        game.setRawgId(gameDTO.getRawgId());
        game.setTags(gameDTO.getTags());
        game.setGenres(gameDTO.getGenres());
        game.setDevelopers(gameDTO.getDevelopers());
        game.setPublishers(gameDTO.getPublishers());
        game.setPlatforms(gameDTO.getPlatforms());

        if (gameDTO.getThumbnail() != null) {
            game.setThumbnail(gameDTO.getThumbnail());
        }

        if (gameDTO.getReleaseDate().isPresent()) {
            game.setReleaseDate(gameDTO.getReleaseDate().get());
        }

        return game;
    }
}
