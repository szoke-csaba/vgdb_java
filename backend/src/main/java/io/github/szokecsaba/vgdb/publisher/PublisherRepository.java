package io.github.szokecsaba.vgdb.publisher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Set<Publisher> searchByNameContainingIgnoreCase(String name);
}
