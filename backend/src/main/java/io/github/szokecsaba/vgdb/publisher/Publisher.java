package io.github.szokecsaba.vgdb.publisher;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "publisher_sequence"
    )
    @SequenceGenerator(name = "publisher_sequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
