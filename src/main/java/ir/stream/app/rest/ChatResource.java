package ir.stream.app.rest;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Guest;
import ir.stream.app.entity.Room;
import ir.stream.app.entity.RoomUser;
import ir.stream.app.entity.User;
import ir.stream.app.service.GuestService;
import ir.stream.app.service.RoomService;
import ir.stream.app.service.RoomUserService;
import ir.stream.app.service.UserService;
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


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatDTO receivePublicMessage(@Payload ChatDTO chatDTO) {
        return chatDTO;
    }

    @MessageMapping("/member")
    @SendTo("/chatroom/members")
    public MemberDTO joinMember(@Payload MemberDTO memberDTO) {
        if (memberDTO.getName() != null && !memberDTO.getName().isEmpty()) {
            // Todo: room Must be dynamic
            Room room = roomService.getById(1L);

            if (memberDTO.isUser()) {
                User user = userService.findByUsername(memberDTO.getName());
                roomUserService.save(new RoomUser(room, user));
            } else {
                guestService.save(new Guest(memberDTO.getName(), room));
            }
            return memberDTO;
        } else {
            return new MemberDTO("", false);
        }
    }


    /*@MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/amir/private
        return message;
    }*/
}
