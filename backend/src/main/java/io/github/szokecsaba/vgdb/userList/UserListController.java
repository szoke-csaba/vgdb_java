package io.github.szokecsaba.vgdb.userList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user-lists")
public class UserListController {
    private final UserListService userListService;

    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<?> changeListType(Principal principal, @PathVariable long gameId,
                                            @RequestBody Map<String, String> listType) {

        return userListService.changeListType(principal.getName(), gameId, listType.get("type"));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getListType(Principal principal, @PathVariable long gameId) {
        return userListService.getListType(principal.getName(), gameId);
    }
}
