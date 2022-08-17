package io.github.szokecsaba.vgdb.genre;

import io.github.szokecsaba.vgdb.util.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final PagingService pagingService;

    @Autowired
    public GenreService(GenreRepository genreRepository, PagingService pagingService) {
        this.genreRepository = genreRepository;
        this.pagingService = pagingService;
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingService.setPage(page);
        pagingService.setPageSize(pageSize);

        Pageable pageable = pagingService.getPageable();
        Page<Genre> genres = genreRepository.findAll(pageable);
        Map<String, Object> response = pagingService.getResponse(genres, "genres");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + id));

        return ResponseEntity.ok().body(genre);
    }

    public ResponseEntity<?> create(Genre genre) {
        genreRepository.save(genre);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Genre genreUpdated, long id) {
        if (genreRepository.findById(id).isEmpty()) {
            throw new GenreNotFoundException("Genre not found with id: " + id);
        }

        genreUpdated.setId(id);
        genreRepository.save(genreUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre not found with id: " + id));

        genreRepository.delete(genre);

        return ResponseEntity.ok().build();
    }
}
