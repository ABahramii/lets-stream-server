package ir.stream.app.repository;

import ir.stream.app.dto.RoomDTO;
import ir.stream.app.dto.RoomFetchDTO;
import ir.stream.app.entity.Room;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends AbstractCrudRepository<Room, Long> {

    @Query("select room.UUID from Room room where room.name=:name and room.active=true and room.privateRoom=false")
    Optional<String> findRoomUUIDByName(String name);

    long countRoomByUUID(String uuid);

    long countRoomByUUIDAndOwnerUsername(String uuid, String ownerUsername);

    @Query(
            "select new ir.stream.app.dto.RoomFetchDTO(room.UUID, room.name, user.username, room.memberCount) " +
            "from Room room " +
            "inner join room.owner user " +
            "where room.active=true " +
            "and room.privateRoom=false"
    )
    List<RoomFetchDTO> findPublicRooms();

    @Query(
            "select new ir.stream.app.dto.RoomDTO(room.name, room.active, room.imageName, room.privateRoom, room.privateCode) " +
                    "from Room room " +
                    "inner join room.owner user " +
                    "where room.UUID=:uuid " +
                    "and user.username=:username"
    )
    RoomDTO findRoomByUUIDAOwnerUsername(String uuid, String username);

    @Query(
            "select room.image from Room room where room.UUID=:UUID"
    )
    byte[] findRoomImageByUUID(String UUID);
}