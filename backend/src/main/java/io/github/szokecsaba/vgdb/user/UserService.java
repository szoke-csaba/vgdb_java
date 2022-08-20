package io.github.szokecsaba.vgdb.user;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.game.GameNotFoundException;
import io.github.szokecsaba.vgdb.game.GameRepository;
import io.github.szokecsaba.vgdb.security.TokenGenerator;
import io.github.szokecsaba.vgdb.util.PagingUtil;
import io.github.szokecsaba.vgdb.vote.Vote;
import io.github.szokecsaba.vgdb.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

import static io.github.szokecsaba.vgdb.game.GameService.GAME_NOT_FOUND;

@Service
public class UserService {
    private static final String USER_NOT_FOUND = "User not found with: ";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final PagingUtil pagingUtil;
    private final GameRepository gameRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator, PagingUtil pagingUtil, GameRepository gameRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.pagingUtil = pagingUtil;
        this.gameRepository = gameRepository;
        this.voteRepository = voteRepository;
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

    @Transactional
    public ResponseEntity<?> vote(String email, long gameId, int vote) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        // TODO: handle from the user directly

        voteRepository.deleteByGameAndUser(game, user);

        if (vote != 0) {
            Vote newVote = new Vote();
            newVote.setVote(vote);
            newVote.setGame(game);
            newVote.setUser(user);
            voteRepository.save(newVote);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getGameVote(String email, long gameId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        // TODO: get it from the user
        Optional<Vote> vote = voteRepository.getVoteByGameAndUser(game, user);
        int gameVote = 0;

        if (vote.isPresent()) {
            gameVote = vote.get().getVote();
        }

        return ResponseEntity.ok().body(gameVote);
    }
}
