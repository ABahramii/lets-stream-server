package ir.stream.app.repository;

import ir.stream.app.dto.ChatDTO;
import ir.stream.app.entity.Chat;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends AbstractCrudRepository<Chat, Long> {

    @Query(
            "select new ir.stream.app.dto.ChatDTO(chat.message, chat.time, chat.senderName, chat.senderIsUser) from Chat chat " +
            "inner join chat.room room " +
            "where room.UUID=:uuid"
    )
    List<ChatDTO> findChatsByRoomUUID(String uuid);
}
