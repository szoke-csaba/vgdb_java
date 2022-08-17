package io.github.szokecsaba.vgdb.platform;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlatformService {
    private final PlatformRepository platformRepository;
    private final PagingService pagingService;

    @Autowired
    public PlatformService(PlatformRepository platformRepository, PagingService pagingService) {
        this.platformRepository = platformRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Platform> platforms = platformRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(platforms, "platforms");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found with id: " + id));

        return ResponseEntity.ok().body(platform);
    }

    public ResponseEntity<?> create(Platform platform) {
        platformRepository.save(platform);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Platform platformUpdated, long id) {
        if (platformRepository.findById(id).isEmpty()) {
            throw new PlatformNotFoundException("Platform not found with id: " + id);
        }

        platformUpdated.setId(id);
        platformRepository.save(platformUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new PlatformNotFoundException("Platform not found with id: " + id));

        platformRepository.delete(platform);

        return ResponseEntity.ok().build();
    }
}
