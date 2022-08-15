package io.github.szokecsaba.vgdb.screenshot;

import io.github.szokecsaba.vgdb.game.Game;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "screenshots")
public class Screenshot {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}