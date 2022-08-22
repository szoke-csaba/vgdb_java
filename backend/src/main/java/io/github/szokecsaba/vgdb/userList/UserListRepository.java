package io.github.szokecsaba.vgdb.userList;

import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserListRepository extends JpaRepository<UserList, Long> {
    Optional<UserList> getUserListByGameAndUser(Game game, User user);
    void deleteByGameAndUser(Game game, User user);
}
