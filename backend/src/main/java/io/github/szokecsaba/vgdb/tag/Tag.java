package io.github.szokecsaba.vgdb.tag;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
}
