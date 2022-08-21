package io.github.szokecsaba.vgdb.vote;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.game.GameNotFoundException;
import io.github.szokecsaba.vgdb.game.GameRepository;
import io.github.szokecsaba.vgdb.user.User;
import io.github.szokecsaba.vgdb.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static io.github.szokecsaba.vgdb.game.GameService.GAME_NOT_FOUND;
import static io.github.szokecsaba.vgdb.user.UserService.USER_NOT_FOUND;

@Service
public class VoteService {
    private final GameRepository gameRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteService(GameRepository gameRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> addVote(String email, long gameId, int vote) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        voteRepository.deleteByGameAndUser(game, user);

        if (vote != 0) {
            Vote newVote = new Vote(0, vote, game, user);
            voteRepository.save(newVote);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getVote(String email, long gameId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        Optional<Vote> vote = voteRepository.getVoteByGameAndUser(game, user);
        int gameVote = 0;

        if (vote.isPresent()) {
            gameVote = vote.get().getVote();
        }

        return ResponseEntity.ok().body(gameVote);
    }
}
