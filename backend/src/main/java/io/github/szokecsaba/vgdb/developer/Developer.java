package io.github.szokecsaba.vgdb.developer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "developer_sequence"
    )
    @SequenceGenerator(name = "developer_sequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
