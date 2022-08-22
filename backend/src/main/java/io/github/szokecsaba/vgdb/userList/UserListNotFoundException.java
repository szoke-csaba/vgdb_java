package io.github.szokecsaba.vgdb.userList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserListNotFoundException extends RuntimeException {
    public UserListNotFoundException(String message) {
        super(message);
    }
}
