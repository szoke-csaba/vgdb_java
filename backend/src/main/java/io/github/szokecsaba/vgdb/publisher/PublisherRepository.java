package io.github.szokecsaba.vgdb.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Set<Publisher> searchByNameContainingIgnoreCase(String name);
}
