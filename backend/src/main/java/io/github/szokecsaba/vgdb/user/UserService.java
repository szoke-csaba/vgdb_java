package io.github.szokecsaba.vgdb.user;

import io.github.szokecsaba.vgdb.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    public static final String USER_NOT_FOUND = "User not found with: ";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PagingUtil pagingUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PagingUtil pagingUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<User> users = userRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(users, "users");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + id));

        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> update(User userUpdated, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + id));

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
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
