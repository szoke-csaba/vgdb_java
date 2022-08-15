package io.github.szokecsaba.vgdb.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.szokecsaba.vgdb.developer.Developer;
import io.github.szokecsaba.vgdb.genre.Genre;
import io.github.szokecsaba.vgdb.platform.Platform;
import io.github.szokecsaba.vgdb.publisher.Publisher;
import io.github.szokecsaba.vgdb.screenshot.Screenshot;
import io.github.szokecsaba.vgdb.tag.Tag;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "games")
public class Game {
    private static final String LOCAL_DATE_TIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String title;

    @NotNull
    private long rawgId;

    private String backgroundImage;

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "game", cascade = { CascadeType.ALL })
    private Set<Screenshot> screenshots;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_tag",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<Developer> developers;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_publisher",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<Publisher> publishers;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<Platform> platforms;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_DEFAULT_FORMAT)
    private LocalDateTime created;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_DEFAULT_FORMAT)
    private LocalDateTime updated;
}
