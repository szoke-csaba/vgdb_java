package io.github.szokecsaba.vgdb.vote;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> getVoteByGameAndUser(Game game, User user);
    void deleteByGameAndUser(Game game, User user);
}
