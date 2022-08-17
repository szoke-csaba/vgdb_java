package io.github.szokecsaba.vgdb.publisher;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PagingService pagingService;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, PagingService pagingService) {
        this.publisherRepository = publisherRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Publisher> publishers = publisherRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(publishers, "publishers");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));

        return ResponseEntity.ok().body(publisher);
    }

    public ResponseEntity<?> create(Publisher publisher) {
        publisherRepository.save(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Publisher publisherUpdated, long id) {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new PublisherNotFoundException("Publisher not found with id: " + id);
        }

        publisherUpdated.setId(id);
        publisherRepository.save(publisherUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));

        publisherRepository.delete(publisher);

        return ResponseEntity.ok().build();
    }
}
