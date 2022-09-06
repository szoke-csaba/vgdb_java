package io.github.szokecsaba.vgdb.review;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.game.GameNotFoundException;
import io.github.szokecsaba.vgdb.game.GameRepository;
import io.github.szokecsaba.vgdb.user.User;
import io.github.szokecsaba.vgdb.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static io.github.szokecsaba.vgdb.game.GameService.GAME_NOT_FOUND;
import static io.github.szokecsaba.vgdb.user.UserService.USER_NOT_FOUND;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> post(String email, long gameId, String text) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + gameId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND + gameId));

        Review review = new Review(0, text, user, game, null);
        reviewRepository.save(review);

        return ResponseEntity.ok().build();
    }
}
