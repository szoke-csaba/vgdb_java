package io.github.szokecsaba.vgdb.tag;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Set<Tag> searchByNameContainingIgnoreCase(String name);
}
