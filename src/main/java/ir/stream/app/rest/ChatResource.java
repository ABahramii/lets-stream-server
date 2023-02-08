package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.*;
import ir.stream.app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatResource {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final RoomUserService roomUserService;
    private final RoomService roomService;
    private final GuestService guestService;
    private final ChatService chatService;


    // Todo: add security filter for send data
    @MessageMapping("/chat/{roomUUID}")
    public void receiveChat(@Payload ChatDTO chatDTO, @DestinationVariable String roomUUID) {
        if (chatDTO != null && chatDTO.getMessage() != null) {
            Room room = roomService.findByUUID(roomUUID);
            Chat chat = new Chat(chatDTO.getMessage(), chatDTO.getTime(), chatDTO.getSenderName(), chatDTO.isSenderIsUser(), room);
            chatService.save(chat);
            simpMessagingTemplate.convertAndSend("/room/chats/" + roomUUID, chatDTO);
        }
    }

    @MessageMapping("/member/{roomUUID}")
    public void joinMember(@Payload MemberDTO memberDTO, @DestinationVariable String roomUUID) {
        if (memberDTO.getName() != null && !memberDTO.getName().isEmpty()) {
            Room room = roomService.findByUUID(roomUUID);

            if (memberDTO.isUser()) {
                User user = userService.findByUsername(memberDTO.getName());
                if (roomUserService.userCanJoinToRoom(user.getUsername(), roomUUID)) {
                    roomUserService.save(new RoomUser(room, user));
                    roomService.increaseMemberCount(room);
                    simpMessagingTemplate.convertAndSend("/room/members/" + roomUUID, memberDTO);
                }
            } else {
                if (guestService.guestCanJoinToRoom(memberDTO.getName(), roomUUID)) {
                    guestService.save(new Guest(memberDTO.getName(), room));
                    roomService.increaseMemberCount(room);
                    simpMessagingTemplate.convertAndSend("/room/members/" + roomUUID, memberDTO);
                }
            }
        }
    }


    @MessageMapping("/member/leave/{roomUUID}")
    public void leaveMember(@Payload MemberDTO memberDTO, @DestinationVariable String roomUUID) {
        if (memberDTO.getName() != null && !memberDTO.getName().isEmpty()) {

            if (memberDTO.isUser()) {
                RoomUser roomUser = roomUserService.findByUsernameAndRoomUUID(memberDTO.getName(), roomUUID);
                if (roomUser != null) {
                    roomUserService.delete(roomUser);
                }
            } else {
                Guest guest = guestService.findByNameAndRoomUUID(memberDTO.getName(), roomUUID);
                if (guest != null) {
                    guestService.delete(guest);
                }
            }
            Room room = roomService.findByUUID(roomUUID);
            roomService.decreaseMemberCount(room);
            simpMessagingTemplate.convertAndSend("/room/members/leave/" + roomUUID, memberDTO);
        }
    }

}
