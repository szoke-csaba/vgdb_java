package io.github.szokecsaba.vgdb.screenshot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "screenshots")
@AllArgsConstructor
@NoArgsConstructor
public class Screenshot {
    private static final String SCREENSHOT_URL = "http://localhost:8081/api/images/games/screenshots/";

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "screenshot_sequence"
    )
    @SequenceGenerator(name = "screenshot_sequence")
    private long id;

    @NotBlank
    private String name;

    public String getAbsoluteUrl() {
        return SCREENSHOT_URL + name;
    }
}
