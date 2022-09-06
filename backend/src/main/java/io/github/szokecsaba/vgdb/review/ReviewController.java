package io.github.szokecsaba.vgdb.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<?> post(Principal principal, @PathVariable long gameId,
                                     @RequestBody Map<String, String> text) {
        return reviewService.post(principal.getName(), gameId, text.get("text"));
    }
}
