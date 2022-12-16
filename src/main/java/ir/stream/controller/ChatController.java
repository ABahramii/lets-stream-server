package ir.stream.controller;

import ir.stream.dto.Message;
import ir.stream.dto.User;
import ir.stream.utils.CacheUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    /*private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }*/

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message) {
        CacheUtils.roomMessageMap.get(1L).add(message);
        return message;
    }

    @MessageMapping("/member")
    @SendTo("/chatroom/members")
    public User tt(@Payload User member) {
        return member;
    }



    /*@MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/amir/private
        return message;
    }*/
}
