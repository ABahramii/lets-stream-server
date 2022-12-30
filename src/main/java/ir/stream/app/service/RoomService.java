package ir.stream.app.service;

import ir.stream.app.entity.Room;
import ir.stream.app.repository.RoomRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends AbstractService<Room, Long, RoomRepository> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository abstractRepository, RoomRepository roomRepository) {
        super(abstractRepository);
        this.roomRepository = roomRepository;
    }

}
