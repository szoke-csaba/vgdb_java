package io.github.szokecsaba.vgdb.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "games")
public class Game {
    private static final String LOCAL_DATE_TIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String title;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_DEFAULT_FORMAT)
    private LocalDateTime created;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_DEFAULT_FORMAT)
    private LocalDateTime updated;
}
