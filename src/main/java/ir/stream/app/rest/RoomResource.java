package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.dto.RoomDTO;
import ir.stream.app.dto.RoomFetchDTO;
import ir.stream.app.entity.Room;
import ir.stream.app.service.*;
import ir.stream.app.utils.JwtUtils;
import ir.stream.core.dto.HttpResponse;
import ir.stream.core.dto.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final UserService userService;
    private final JwtUtils jwtUtils;

    // Todo: find top 10 rooms
    @GetMapping
    public ResponseEntity<HttpResponse<List<RoomFetchDTO>>> findPublicRooms() {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findPublicRooms()));
    }

    @GetMapping("/fetch/{uuid}")
    public ResponseEntity<HttpResponse<RoomDTO>> findRoomByUUID(@PathVariable String uuid, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        return ResponseEntity.ok(new HttpResponse<>(roomService.findRoomByUUIDAOwnerUsername(uuid, username)));
    }
    
    @PostMapping("/create")
    public ResponseEntity<HttpResponseStatus> create(@ModelAttribute RoomDTO roomDto,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws IOException {
        String token = authHeader.split(" ")[1];
        String username = jwtUtils.extractUsername(token);

        Room room = new Room();
        room.setName(roomDto.getName());
        room.setOwner(userService.findByUsername(username));
        room.setActive(roomDto.isActive());
        room.setImage(roomDto.getImage().getBytes());
        room.setImageName(roomDto.getImageName());
        room.setPrivateRoom(roomDto.isPrivateRoom());
        room.setPrivateCode(roomDto.getPrivateCode());
        roomService.save(room);
        return ResponseEntity.ok(new HttpResponseStatus("ok", 200));
    }

    @PutMapping("/edit/{uuid}")
    public ResponseEntity<HttpResponseStatus> edit(@ModelAttribute RoomDTO roomDto, @PathVariable String uuid,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws IOException {
        String token = authHeader.split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        roomService.edit(roomDto, uuid, username);
        return ResponseEntity.ok(new HttpResponseStatus("ok", 200));
    }

    @GetMapping("/image/{uuid}")
    public ResponseEntity<?> findRoomImage(@PathVariable String uuid) {
        Resource resource = new ByteArrayResource(roomService.findRoomImageByUUID(uuid));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> checkJoin(@PathVariable String uuid,
                                                                        @RequestBody MemberDTO memberDTO) {
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
    public ResponseEntity<HttpResponse<Map<String, Boolean>>> userIsRoomOwner(@PathVariable String uuid,
                                                                              @PathVariable String token) {
        try {
            if (!jwtUtils.isTokenExpired(token)) {
                String username = jwtUtils.extractUsername(token);
                boolean isRoomOwner = roomService.userIsRoomOwner(uuid, username);
                return ResponseEntity.ok(new HttpResponse<>(Map.of("isRoomOwner", isRoomOwner)));
            }
            return ResponseEntity.ok(new HttpResponse<>(Map.of("isRoomOwner", false)));
        } catch (Exception e) {
            return ResponseEntity.ok(new HttpResponse<>(Map.of("isRoomOwner", false)));
        }
    }

}
