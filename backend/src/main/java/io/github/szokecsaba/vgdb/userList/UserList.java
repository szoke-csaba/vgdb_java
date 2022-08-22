package io.github.szokecsaba.vgdb.userList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.szokecsaba.vgdb.game.Game;
import io.github.szokecsaba.vgdb.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_lists")
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_list_sequence"
    )
    @SequenceGenerator(name = "user_list_sequence")
    private long id;

    private UserListType type;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
