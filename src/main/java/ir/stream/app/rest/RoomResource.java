package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Room;
import ir.stream.app.service.ChatService;
import ir.stream.app.service.RoomService;
import ir.stream.core.dto.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomResource {

    private final RoomService roomService;
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<HttpResponse<List<Room>>> findALl() {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findAll()));
    }

    @GetMapping("/{uuid}/members")
    public ResponseEntity<HttpResponse<List<MemberDTO>>> findRoomMembers(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(roomService.findRoomMembers(uuid)));
    }

    @GetMapping("/{uuid}/chats")
    public ResponseEntity<HttpResponse<List<ChatDTO>>> findRoomChats(@PathVariable String uuid) {
        return ResponseEntity.ok(new HttpResponse<>(chatService.findChatsByRoomUUID(uuid)));
    }

}
