package ir.stream.app.service;

import ir.stream.app.entity.Chat;
import ir.stream.app.repository.ChatRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends AbstractService<Chat, Long, ChatRepository> {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository abstractRepository, ChatRepository chatRepository) {
        super(abstractRepository);
        this.chatRepository = chatRepository;
    }

}
