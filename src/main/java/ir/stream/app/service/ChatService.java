package ir.stream.app.service;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.entity.Chat;
import ir.stream.app.repository.ChatRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService extends AbstractService<Chat, Long, ChatRepository> {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository abstractRepository, ChatRepository chatRepository) {
        super(abstractRepository);
        this.chatRepository = chatRepository;
    }

    public List<ChatDTO> findChatsByRoomUUID(String uuid) {
        return chatRepository.findChatsByRoomUUID(uuid);
    }

}
