package io.github.szokecsaba.vgdb.platform;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Set<Platform> searchByNameContainingIgnoreCase(String name);
}
