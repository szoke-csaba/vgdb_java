package io.github.szokecsaba.vgdb.developer;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
