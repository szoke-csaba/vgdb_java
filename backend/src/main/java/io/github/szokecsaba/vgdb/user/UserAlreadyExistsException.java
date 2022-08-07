package io.github.szokecsaba.vgdb.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exists with that email!")
public class UserAlreadyExistsException extends RuntimeException {}
