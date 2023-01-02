package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.*;
import ir.stream.app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatResource {

    private final UserService userService;
    private final RoomUserService roomUserService;
    private final RoomService roomService;
    private final GuestService guestService;
    private final ChatService chatService;


    // Todo: add security filter for send data
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatDTO receivePublicMessage(@Payload ChatDTO chatDTO) {
        if (chatDTO != null && chatDTO.getMessage() != null) {
            Room room = roomService.getById(1L);
            Chat chat = new Chat(chatDTO.getMessage(), chatDTO.getTime(), chatDTO.getSenderName(), chatDTO.isSenderIsUser(), room);
            chatService.save(chat);
            return chatDTO;
        } else {
            return new ChatDTO();
        }
    }

    @MessageMapping("/member")
    @SendTo("/chatroom/members")
    public MemberDTO joinMember(@Payload MemberDTO memberDTO) {
        if (memberDTO.getName() != null && !memberDTO.getName().isEmpty()) {
            // Todo: room Must be dynamic
            Room room = roomService.getById(1L);

            if (memberDTO.isUser()) {
                // Todo: add exception handling for userNotFound
                User user = userService.findByUsername(memberDTO.getName());
                roomUserService.save(new RoomUser(room, user));
            } else {
                guestService.save(new Guest(memberDTO.getName(), room));
            }
            return memberDTO;
        } else {
            return new MemberDTO();
        }
    }


    /*@MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/amir/private
        return message;
    }*/
}
