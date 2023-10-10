package io.github.szokecsaba.vgdb.developer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        return developerService.searchByName(name);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer pageSize) {
        return developerService.getAll(page, pageSize);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> get(@PathVariable long id) {
        return developerService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Developer developer) {
        return developerService.create(developer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Developer developer, @PathVariable long id) {
        return developerService.update(developer, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return developerService.delete(id);
    }
}
