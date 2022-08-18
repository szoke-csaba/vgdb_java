package io.github.szokecsaba.vgdb.developer;

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
public class DeveloperService {
    private static final String DEVELOPER_NOT_FOUND = "Developer not found with id: ";

    private final DeveloperRepository developerRepository;
    private final PagingUtil pagingUtil;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, PagingUtil pagingUtil) {
        this.developerRepository = developerRepository;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Developer> developers = developerRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(developers);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Developer> developers = developerRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(developers, "developers");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(DEVELOPER_NOT_FOUND + id));

        return ResponseEntity.ok().body(developer);
    }

    public ResponseEntity<?> create(Developer developer) {
        developerRepository.save(developer);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Developer developerUpdated, long id) {
        if (developerRepository.findById(id).isEmpty()) {
            throw new DeveloperNotFoundException(DEVELOPER_NOT_FOUND + id);
        }

        developerUpdated.setId(id);
        developerRepository.save(developerUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(DEVELOPER_NOT_FOUND + id));

        developerRepository.delete(developer);

        return ResponseEntity.ok().build();
    }
}
