package io.github.szokecsaba.vgdb.screenshot;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "screenshots")
@AllArgsConstructor
@NoArgsConstructor
public class Screenshot {
    private static final String SCREENSHOT_URL = "http://localhost:8081/api/images/games/screenshots/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    public String getAbsoluteUrl() {
        return SCREENSHOT_URL + name;
    }
}
