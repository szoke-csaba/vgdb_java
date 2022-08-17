package io.github.szokecsaba.vgdb.developer;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final PagingService pagingService;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, PagingService pagingService) {
        this.developerRepository = developerRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Developer> developers = developerRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(developers, "developers");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not found with id: " + id));

        return ResponseEntity.ok().body(developer);
    }

    public ResponseEntity<?> create(Developer developer) {
        developerRepository.save(developer);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Developer developerUpdated, long id) {
        if (developerRepository.findById(id).isEmpty()) {
            throw new DeveloperNotFoundException("Developer not found with id: " + id);
        }

        developerUpdated.setId(id);
        developerRepository.save(developerUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not found with id: " + id));

        developerRepository.delete(developer);

        return ResponseEntity.ok().build();
    }
}
