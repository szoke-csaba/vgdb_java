package io.github.szokecsaba.vgdb.screenshot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {}
