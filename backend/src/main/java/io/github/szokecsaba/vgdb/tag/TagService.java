package io.github.szokecsaba.vgdb.tag;

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
public class TagService {
    private static final String TAG_NOT_FOUND = "Tag not found with id: ";

    private final TagRepository tagRepository;
    private final PagingUtil pagingUtil;

    @Autowired
    public TagService(TagRepository tagRepository, PagingUtil pagingUtil) {
        this.tagRepository = tagRepository;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Tag> tags = tagRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(tags);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Tag> tags = tagRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(tags, "tags");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(TAG_NOT_FOUND + id));

        return ResponseEntity.ok().body(tag);
    }

    public ResponseEntity<?> create(Tag tag) {
        tagRepository.save(tag);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Tag tagUpdated, long id) {
        if (tagRepository.findById(id).isEmpty()) {
            throw new TagNotFoundException(TAG_NOT_FOUND + id);
        }

        tagUpdated.setId(id);
        tagRepository.save(tagUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(TAG_NOT_FOUND + id));

        tagRepository.delete(tag);

        return ResponseEntity.ok().build();
    }
}
