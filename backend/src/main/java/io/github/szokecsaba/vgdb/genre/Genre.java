package io.github.szokecsaba.vgdb.genre;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
