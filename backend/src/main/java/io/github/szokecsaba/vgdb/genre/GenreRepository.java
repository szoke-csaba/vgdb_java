package io.github.szokecsaba.vgdb.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> searchByNameContainingIgnoreCase(String name);
}
