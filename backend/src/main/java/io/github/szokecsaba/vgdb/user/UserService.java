package io.github.szokecsaba.vgdb.user;

import io.github.szokecsaba.vgdb.security.TokenGenerator;
import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final PagingService pagingService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator, PagingService pagingService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.pagingService = pagingService;
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        LoginResponse response = new LoginResponse(user.getEmail(), user.getRole(), tokenGenerator.generate(user));

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<User> users = userRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(users, "users");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> update(User userUpdated, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(userUpdated.getEmail()) && userRepository.existsByEmail(userUpdated.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        if (!userUpdated.getPassword().isEmpty()) {
            userUpdated.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
        } else {
            userUpdated.setPassword(user.getPassword());
        }

        userUpdated.setId(id);
        userUpdated.setCreated(user.getCreated());
        userRepository.save(userUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
