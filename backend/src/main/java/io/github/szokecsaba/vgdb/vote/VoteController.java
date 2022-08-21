package io.github.szokecsaba.vgdb.vote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<?> addVote(Principal principal, @PathVariable long gameId,
                                     @RequestBody Map<String, Integer> vote) {
        return voteService.addVote(principal.getName(), gameId, vote.get("vote"));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getVote(Principal principal, @PathVariable long gameId) {
        return voteService.getVote(principal.getName(), gameId);
    }
}
