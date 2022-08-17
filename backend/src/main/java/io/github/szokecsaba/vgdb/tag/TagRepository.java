package io.github.szokecsaba.vgdb.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Set<Tag> searchByNameContainingIgnoreCase(String name);
}
