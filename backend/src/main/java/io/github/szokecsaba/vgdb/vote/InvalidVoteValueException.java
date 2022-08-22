package io.github.szokecsaba.vgdb.vote;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVoteValueException extends RuntimeException {
    public InvalidVoteValueException(String message) {
        super(message);
    }
}
