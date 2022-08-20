package io.github.szokecsaba.vgdb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/vote/{gameId}")
    public ResponseEntity<?> vote(Authentication authentication, @PathVariable long gameId,
                                  @RequestBody Map<String, Integer> vote) {
        return userService.vote(authentication.getName(), gameId, vote.get("vote"));
    }

    @GetMapping("/users/vote/{gameId}")
    public ResponseEntity<?> getGameVote(Authentication authentication, @PathVariable long gameId) {
        return userService.getGameVote(authentication.getName(), gameId);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUserDetailsAfterLogin(Principal user) {
        return userService.login(user.getName());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer pageSize) {
        return userService.getAll(page, pageSize);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> get(@PathVariable long id) {
        return userService.get(id);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody User user, @PathVariable long id) {
        return userService.update(user, id);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return userService.delete(id);
    }
}
