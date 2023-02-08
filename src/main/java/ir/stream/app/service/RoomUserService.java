package ir.stream.app.service;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.RoomUser;
import ir.stream.app.repository.RoomUserRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomUserService extends AbstractService<RoomUser, Long, RoomUserRepository> {

    private final RoomUserRepository roomUserRepository;

    public RoomUserService(RoomUserRepository abstractRepository, RoomUserRepository roomUserRepository) {
        super(abstractRepository);
        this.roomUserRepository = roomUserRepository;
    }

    public List<MemberDTO> findUserMemberDtoListByRoomUUID(String uuid) {
        return roomUserRepository.findUserMemberDtoListByRoomUUID(uuid);
    }

    public boolean userCanJoinToRoom(String username, String roomUUID) {
        return roomUserRepository.countByUsernameAndRoomUUID(username, roomUUID) == 0;
    }

    public RoomUser findByUsernameAndRoomUUID(String username, String roomUUID) {
        return roomUserRepository.findByUsernameAndRoomUUID(username, roomUUID);
    }
}
