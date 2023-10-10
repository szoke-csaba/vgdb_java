package io.github.szokecsaba.vgdb.genre;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence"
    )
    @SequenceGenerator(name = "genre_sequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
