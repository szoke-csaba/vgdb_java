package io.github.szokecsaba.vgdb.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Tag> tags = tagRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(tags);
    }
}
