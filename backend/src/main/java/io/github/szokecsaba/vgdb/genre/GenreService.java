package io.github.szokecsaba.vgdb.genre;

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
public class GenreService {
    private static final String GENRE_NOT_FOUND = "Genre not found with id: ";

    private final GenreRepository genreRepository;
    private final PagingUtil pagingUtil;

    @Autowired
    public GenreService(GenreRepository genreRepository, PagingUtil pagingUtil) {
        this.genreRepository = genreRepository;
        this.pagingUtil = pagingUtil;
    }

    public ResponseEntity<?> searchByName(String name) {
        Set<Genre> genres = genreRepository.searchByNameContainingIgnoreCase(name);

        return ResponseEntity.ok().body(genres);
    }

    public ResponseEntity<?> getAll(Integer page, Integer pageSize) {
        pagingUtil.setPage(page);
        pagingUtil.setPageSize(pageSize);

        Pageable pageable = pagingUtil.getPageable();
        Page<Genre> genres = genreRepository.findAll(pageable);
        Map<String, Object> response = pagingUtil.getResponse(genres, "genres");

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> get(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(GENRE_NOT_FOUND + id));

        return ResponseEntity.ok().body(genre);
    }

    public ResponseEntity<?> create(Genre genre) {
        genreRepository.save(genre);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Genre genreUpdated, long id) {
        if (genreRepository.findById(id).isEmpty()) {
            throw new GenreNotFoundException(GENRE_NOT_FOUND + id);
        }

        genreUpdated.setId(id);
        genreRepository.save(genreUpdated);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> delete(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(GENRE_NOT_FOUND + id));

        genreRepository.delete(genre);

        return ResponseEntity.ok().build();
    }
}
