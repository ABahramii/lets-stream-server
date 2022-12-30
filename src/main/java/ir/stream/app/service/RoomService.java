package ir.stream.app.service;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Room;
import ir.stream.app.repository.RoomRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService extends AbstractService<Room, Long, RoomRepository> {

    private final RoomUserService roomUserService;
    private final GuestService guestService;

    public RoomService(RoomRepository abstractRepository, RoomUserService roomUserService, GuestService guestService) {
        super(abstractRepository);
        this.roomUserService = roomUserService;
        this.guestService = guestService;
    }

    public List<MemberDTO> findRoomMembers(String uuid) {
        List<MemberDTO> memberDtoList = new ArrayList<>();
        memberDtoList.addAll(roomUserService.findUserMemberDtoListByRoomUUID(uuid));
        memberDtoList.addAll(guestService.findGuestMemberDtoListByRoomUUID(uuid));
        return memberDtoList;
    }
}
