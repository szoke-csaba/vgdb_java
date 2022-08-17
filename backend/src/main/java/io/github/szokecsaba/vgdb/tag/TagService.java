package io.github.szokecsaba.vgdb.tag;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final PagingService pagingService;

    @Autowired
    public TagService(TagRepository tagRepository, PagingService pagingService) {
        this.tagRepository = tagRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Tag> tags = tagRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(tags);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Tag> tags = tagRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(tags, "tags");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));

        return ResponseEntity.ok().body(tag);
    }

    public ResponseEntity<?> create(Tag tag) {
        tagRepository.save(tag);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Tag tagUpdated, long id) {
        if (tagRepository.findById(id).isEmpty()) {
            throw new TagNotFoundException("Tag not found with id: " + id);
        }

        tagUpdated.setId(id);
        tagRepository.save(tagUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("Tag not found with id: " + id));

        tagRepository.delete(tag);

        return ResponseEntity.ok().build();
    }
}
