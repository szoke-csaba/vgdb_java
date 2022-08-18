package io.github.szokecsaba.vgdb.game;

import io.github.szokecsaba.vgdb.developer.Developer;
import io.github.szokecsaba.vgdb.genre.Genre;
import io.github.szokecsaba.vgdb.platform.Platform;
import io.github.szokecsaba.vgdb.publisher.Publisher;
import io.github.szokecsaba.vgdb.tag.Tag;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Data
public class GameDTO {
    private String title;
    private String thumbnail;
    private long rawgId;
    private String releaseDate;
    private Set<Tag> tags;
    private Set<Genre> genres;
    private Set<Developer> developers;
    private Set<Publisher> publishers;
    private Set<Platform> platforms;
    private MultipartFile thumbnailFile;

    public Optional<LocalDate> getReleaseDate() {
        LocalDate date = releaseDate == null ? null : LocalDate.parse(releaseDate);

        return Optional.ofNullable(date);
    }
}
