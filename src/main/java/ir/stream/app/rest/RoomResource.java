package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Room;
import ir.stream.app.service.*;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomResource {

    private final RoomService roomService;
    private final ChatService chatService;
    private final RoomUserService roomUserService;
    private final GuestService guestService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<HttpResponse<List<Room>>> findALl() {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findAll()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<HttpResponse<String>> findRoomUuidByName(@PathVariable String name) {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findRoomUUIDByName(name)));
    }

    @GetMapping("/exists/{uuid}")
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> roomExists(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(Map.of("exists", roomService.roomExists(uuid))));
    }

    @GetMapping("/{uuid}/members")
    public ResponseEntity<HttpResponse<List<MemberDTO>>> findRoomMembers(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findRoomMembers(uuid)));
    }

    @GetMapping("/{uuid}/chats")
    public ResponseEntity<HttpResponse<List<ChatDTO>>> findRoomChats(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(chatService.findChatsByRoomUUID(uuid)));
    }

    @PostMapping("/{uuid}/checkJoin")
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> checkJoin(@PathVariable String uuid, @RequestBody MemberDTO memberDTO) {
        boolean canJoin;
        if (memberDTO.isUser()) {
            canJoin = roomUserService.userCanJoinToRoom(memberDTO.getName(), uuid);
        } else {
            canJoin = guestService.guestCanJoinToRoom(memberDTO.getName(), uuid);
        }
        return ResponseEntity.ok(new HttpResponse<>(Map.of("canJoin", canJoin)));
    }

    // Todo: must be refactor
    @GetMapping("{uuid}/userIsRoomOwner/{token}")
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> userIsRoomOwner(@PathVariable String uuid, @PathVariable String token) {
        if (!jwtUtils.isTokenExpired(token)) {
            String username = jwtUtils.extractUsername(token);
            boolean isRoomOwner = roomService.userIsRoomOwner(uuid, username);
            return ResponseEntity.ok(new HttpResponse<>(Map.of("isRoomOwner", isRoomOwner)));
        }
        return ResponseEntity.ok(new HttpResponse<>(Map.of("isRoomOwner", false)));
    }

}
