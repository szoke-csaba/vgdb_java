package io.github.szokecsaba.vgdb.publisher;

import io.github.szokecsaba.vgdb.genre.Genre;
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
public class PublisherService {
    private static final String PUBLISHER_NOT_FOUND = "Publisher not found with id: ";

    private final PublisherRepository publisherRepository;
    private final PagingUtil pagingUtil;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, PagingUtil pagingUtil) {
        this.publisherRepository = publisherRepository;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Publisher> publishers = publisherRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(publishers);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Publisher> publishers = publisherRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(publishers, "publishers");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND + id));

        return ResponseEntity.ok().body(publisher);
    }

    public ResponseEntity<?> create(Publisher publisher) {
        publisherRepository.save(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Publisher publisherUpdated, long id) {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new PublisherNotFoundException(PUBLISHER_NOT_FOUND + id);
        }

        publisherUpdated.setId(id);
        publisherRepository.save(publisherUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(PUBLISHER_NOT_FOUND + id));

        publisherRepository.delete(publisher);

        return ResponseEntity.ok().build();
    }
}
