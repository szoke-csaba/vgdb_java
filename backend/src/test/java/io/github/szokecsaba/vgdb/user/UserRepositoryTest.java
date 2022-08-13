package io.github.szokecsaba.vgdb.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void findByEmail_True() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("password1234");
        repository.save(user);
        Optional<User> expected = repository.findByEmail("test@email.com");

        assertTrue(expected.isPresent());
        assertNotNull(expected.get().getEmail());
        assertNotNull(expected.get().getPassword());
        assertNotNull(expected.get().getCreated());
        assertEquals("ROLE_USER", expected.get().getRole());
    }

    @Test
    void findByEmail_False() {
        Optional<User> expected = repository.findByEmail("test@email.com");

        assertFalse(expected.isPresent());
    }
}
