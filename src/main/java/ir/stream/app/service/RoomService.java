package ir.stream.app.service;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.dto.RoomDTO;
import ir.stream.app.dto.RoomFetchDTO;
import ir.stream.app.entity.Room;
import ir.stream.app.repository.RoomRepository;
import ir.stream.core.exception.NotFoundException;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService extends AbstractService<Room, Long, RoomRepository> {

    private final RoomRepository roomRepository;
    private final RoomUserService roomUserService;
    private final GuestService guestService;

    public RoomService(RoomRepository abstractRepository, RoomRepository roomRepository, RoomUserService roomUserService, GuestService guestService) {
        super(abstractRepository);
        this.roomRepository = roomRepository;
        this.roomUserService = roomUserService;
        this.guestService = guestService;
    }

    public List<RoomFetchDTO> findPublicRooms() {
        return roomRepository.findPublicRooms();
    }

    public List<RoomFetchDTO> findUserRooms(String username) {
        return roomRepository.findUserRooms(username);
    }

    public RoomDTO findRoomByUUIDAOwnerUsername(String uuid, String username) {
        return roomRepository.findRoomByUUIDAOwnerUsername(uuid, username);
    }

    public void edit(RoomDTO roomDto, String uuid, String username) throws IOException {
        if (userIsRoomOwner(uuid, username)) {
            Room room = findByUUID(uuid);
            room.setName(roomDto.getName());
            if (roomDto.getImage() != null) {
                room.setImage(roomDto.getImage().getBytes());
                room.setImageName(roomDto.getImageName());
            }
            room.setActive(roomDto.isActive());
            room.setPrivateRoom(roomDto.isPrivateRoom());
            room.setPrivateCode(roomDto.isPrivateRoom() ? roomDto.getPrivateCode() : null);
            save(room);
        } else {
            throw new NotFoundException("You are not owner of this room.");
        }
    }

    public byte[] findRoomImageByUUID(String uuid) {
        return roomRepository.findRoomImageByUUID(uuid);
    }

    public void delete(String uuid, String username) {
        if (userIsRoomOwner(uuid, username)) {
            Room room = findByUUID(uuid);
            delete(room);
        } else {
            throw new NotFoundException("You are not owner of this room.");
        }
    }

    public List<MemberDTO> findRoomMembers(String uuid) {
        List<MemberDTO> memberDtoList = new ArrayList<>();
        memberDtoList.addAll(roomUserService.findUserMemberDtoListByRoomUUID(uuid));
        memberDtoList.addAll(guestService.findGuestMemberDtoListByRoomUUID(uuid));
        return memberDtoList;
    }

    public String findRoomUUIDByName(String name) {
        return roomRepository.findRoomUUIDByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Room with name %s not found !", name)));
    }

    public boolean canSave(String name) {
        return roomRepository.countRoomByName(name) == 0;
    }

    public String findRoomUUIDByPrivateCode(String privateCode) {
        return roomRepository.findRoomUUIDByPrivateCode(privateCode)
                .orElseThrow(() -> new NotFoundException(String.format("Room with private code %s not found !", privateCode)));
    }

    public boolean roomExists(String uuid) {
        return roomRepository.countRoomByUUID(uuid) != 0;
    }

    public boolean userIsRoomOwner(String uuid, String username) {
        return roomRepository.countRoomByUUIDAndOwnerUsername(uuid, username) != 0;
    }

    public void increaseMemberCount(Room room) {
        room.setMemberCount(room.getMemberCount() + 1);
        roomRepository.save(room);
    }

    public void decreaseMemberCount(Room room) {
        long memberCount = room.getMemberCount();
        if (memberCount > 0) {
            room.setMemberCount(memberCount - 1);
            roomRepository.save(room);
        }
    }
}
