package ir.stream.app.service;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Guest;
import ir.stream.app.repository.GuestRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService extends AbstractService<Guest, Long, GuestRepository> {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository abstractRepository, GuestRepository guestRepository) {
        super(abstractRepository);
        this.guestRepository = guestRepository;
    }

    public List<MemberDTO> findGuestMemberDtoListByRoomUUID(String uuid) {
        return guestRepository.findGuestMemberDtoListByRoomUUID(uuid);
    }

    public boolean guestCanJoinToRoom(String guestName, String roomUUID) {
        return guestRepository.countGuestByNameAndRoomUUID(guestName, roomUUID) == 0;
    }
}
