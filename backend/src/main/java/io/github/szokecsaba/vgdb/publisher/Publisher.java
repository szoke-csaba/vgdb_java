package io.github.szokecsaba.vgdb.publisher;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
