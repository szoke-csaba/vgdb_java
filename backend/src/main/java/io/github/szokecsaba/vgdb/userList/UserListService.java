package io.github.szokecsaba.vgdb.userList;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.game.GameNotFoundException;
import io.github.szokecsaba.vgdb.game.GameRepository;
import io.github.szokecsaba.vgdb.user.User;
import io.github.szokecsaba.vgdb.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static io.github.szokecsaba.vgdb.game.GameService.GAME_NOT_FOUND;
import static io.github.szokecsaba.vgdb.user.UserService.USER_NOT_FOUND;

@Service
public class UserListService {
    private final GameRepository gameRepository;
    private final UserListRepository userListRepository;
    private final UserRepository userRepository;

    public UserListService(GameRepository gameRepository, UserListRepository userListRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userListRepository = userListRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> changeListType(String email, long gameId, String listType) {
        if (!listType.equals("0") && Arrays.stream(UserListType.values()).noneMatch((t) -> t.name().equals(listType))) {
            throw new UserListNotFoundException("User list not found: " + listType);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        userListRepository.deleteByGameAndUser(game, user);

        if (!listType.equals("0")) {
            UserList newList = new UserList(0, UserListType.valueOf(listType), game, user);
            userListRepository.save(newList);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getListType(String email, long gameId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        Optional<UserList> vote = userListRepository.getUserListByGameAndUser(game, user);
        String listType = "0";

        if (vote.isPresent()) {
            listType = vote.get().getType().toString();
        }

        return ResponseEntity.ok().body(listType);
    }
}
