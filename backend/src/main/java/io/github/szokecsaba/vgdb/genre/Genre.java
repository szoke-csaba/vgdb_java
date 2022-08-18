package io.github.szokecsaba.vgdb.genre;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
