package io.github.szokecsaba.vgdb.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.github.szokecsaba.vgdb.developer.Developer;
import io.github.szokecsaba.vgdb.genre.Genre;
import io.github.szokecsaba.vgdb.platform.Platform;
import io.github.szokecsaba.vgdb.publisher.Publisher;
import io.github.szokecsaba.vgdb.review.Review;
import io.github.szokecsaba.vgdb.screenshot.Screenshot;
import io.github.szokecsaba.vgdb.tag.Tag;
import io.github.szokecsaba.vgdb.userList.UserList;
import io.github.szokecsaba.vgdb.vote.Vote;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Game {
    private static final String LOCAL_DATE_TIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String THUMBNAIL_URL = "http://localhost:8081/api/images/games/thumbnails/";
    private static final String DEFAULT_THUMBNAIL = "https://picsum.photos/400/225";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<UserList> lists;

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

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Review> reviews;

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
        OptionalDouble average = votes
                .stream()
                .mapToDouble(Vote::getVote)
                .average();

        return Math.round(average.orElse(0) * 100.d) / 100.0d;
    }

    public Map<Integer, Long> getNumberOfVotesPerRating() {
        Map<Integer, Long> response = votes.stream()
                .collect(Collectors.groupingBy(
                        Vote::getVote, Collectors.counting()
                ));

        return fillMissingRatingsWithZero(response);
    }

    public long getMostVotesForARating() {
        Map<Integer, Long> map = getNumberOfVotesPerRating();

        if (map.isEmpty()) {
            return 0;
        }

        return Collections.max(
                map.entrySet(),
                (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())
        ).getValue();
    }

    private Map<Integer, Long> fillMissingRatingsWithZero(Map<Integer, Long> map) {
        Map<Integer, Long> newMap = new HashMap<>();

        for (int i = 1; i <= 10; i++) {
            newMap.put(i, map.getOrDefault(i, 0L));
        }

        return newMap;
    }
}
