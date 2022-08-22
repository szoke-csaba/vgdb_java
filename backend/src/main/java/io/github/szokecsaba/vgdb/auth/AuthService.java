package io.github.szokecsaba.vgdb.auth;

import io.github.szokecsaba.vgdb.security.TokenGenerator;
import io.github.szokecsaba.vgdb.user.User;
import io.github.szokecsaba.vgdb.user.UserAlreadyExistsException;
import io.github.szokecsaba.vgdb.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static io.github.szokecsaba.vgdb.user.UserService.USER_NOT_FOUND;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, TokenGenerator tokenGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> login(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));

        LoginResponse response = new LoginResponse(user.getEmail(), user.getRole(), tokenGenerator.generate(user));

        return ResponseEntity.ok().body(response);
    }
}
