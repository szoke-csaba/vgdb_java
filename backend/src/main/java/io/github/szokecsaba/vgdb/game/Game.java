package io.github.szokecsaba.vgdb.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.szokecsaba.vgdb.developer.Developer;
import io.github.szokecsaba.vgdb.genre.Genre;
import io.github.szokecsaba.vgdb.platform.Platform;
import io.github.szokecsaba.vgdb.publisher.Publisher;
import io.github.szokecsaba.vgdb.screenshot.Screenshot;
import io.github.szokecsaba.vgdb.tag.Tag;
import io.github.szokecsaba.vgdb.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games")
public class Game {
    private static final String LOCAL_DATE_TIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String THUMBNAIL_URL = "http://localhost:8081/api/images/games/thumbnails/";
    private static final String DEFAULT_THUMBNAIL = "https://placeimg.com/400/225/nature";

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    @SequenceGenerator(name = "game_sequence")
    private long id;

    @NotBlank
    private String title;

    @NotNull
    private long rawgId;

    private String thumbnail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "game_id", nullable = false)
    private Set<Screenshot> screenshots;

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Vote> votes;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "game_tag",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<Developer> developers;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "game_publisher",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<Publisher> publishers;

    @ManyToMany(cascade = { CascadeType.MERGE })
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

    public String getThumbnailAbsolute() {
        return thumbnail != null ? THUMBNAIL_URL + thumbnail : DEFAULT_THUMBNAIL;
    }

    public double getAverageRating() {
        OptionalDouble average = votes.stream().mapToDouble(Vote::getVote).average();

        return Math.round(average.orElse(0) * 100.d) / 100.0d;
    }
}
