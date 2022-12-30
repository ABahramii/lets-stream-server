package ir.stream.app.service;

import ir.stream.app.entity.RoomUser;
import ir.stream.app.repository.RoomUserRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class RoomUserService extends AbstractService<RoomUser, Long, RoomUserRepository> {
    public RoomUserService(RoomUserRepository abstractRepository) {
        super(abstractRepository);
    }
}
