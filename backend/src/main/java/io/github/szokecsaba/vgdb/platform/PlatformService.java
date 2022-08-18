package io.github.szokecsaba.vgdb.platform;

import io.github.szokecsaba.vgdb.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class PlatformService {
    private static final String PLATFORM_NOT_FOUND = "Platform not found with id: ";

    private final PlatformRepository platformRepository;
    private final PagingUtil pagingUtil;

    @Autowired
    public PlatformService(PlatformRepository platformRepository, PagingUtil pagingUtil) {
        this.platformRepository = platformRepository;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Platform> platforms = platformRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(platforms);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Platform> platforms = platformRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(platforms, "platforms");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException(PLATFORM_NOT_FOUND + id));

        return ResponseEntity.ok().body(platform);
    }

    public ResponseEntity<?> create(Platform platform) {
        platformRepository.save(platform);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Platform platformUpdated, long id) {
        if (platformRepository.findById(id).isEmpty()) {
            throw new PlatformNotFoundException(PLATFORM_NOT_FOUND + id);
        }

        platformUpdated.setId(id);
        platformRepository.save(platformUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException(PLATFORM_NOT_FOUND + id));

        platformRepository.delete(platform);

        return ResponseEntity.ok().build();
    }
}
