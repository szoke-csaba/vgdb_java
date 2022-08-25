package io.github.szokecsaba.vgdb.auth;

import io.github.szokecsaba.vgdb.userList.UserList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String role;
    private String token;
    private List<UserList> lists;
}
