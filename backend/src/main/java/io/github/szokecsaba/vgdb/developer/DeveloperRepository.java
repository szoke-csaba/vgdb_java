package io.github.szokecsaba.vgdb.developer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Set<Developer> searchByNameContainingIgnoreCase(String name);
}
