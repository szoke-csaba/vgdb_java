package io.github.szokecsaba.vgdb.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String role;
    private String token;
}
