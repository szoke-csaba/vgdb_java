package io.github.szokecsaba.vgdb.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Set<Developer> searchByNameContainingIgnoreCase(String name);
}
