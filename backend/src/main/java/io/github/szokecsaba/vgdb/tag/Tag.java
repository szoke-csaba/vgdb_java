package io.github.szokecsaba.vgdb.tag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    @SequenceGenerator(name = "tag_sequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
