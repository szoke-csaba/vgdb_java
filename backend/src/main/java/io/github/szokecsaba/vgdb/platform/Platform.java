package io.github.szokecsaba.vgdb.platform;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "platforms")
public class Platform {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
