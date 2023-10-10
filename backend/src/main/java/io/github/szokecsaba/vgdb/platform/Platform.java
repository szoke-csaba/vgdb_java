package io.github.szokecsaba.vgdb.platform;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "platforms")
public class Platform {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "platform_sequence"
    )
    @SequenceGenerator(name = "platform_sequence")
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
