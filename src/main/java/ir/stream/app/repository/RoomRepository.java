package ir.stream.app.repository;

import ir.stream.app.dto.RoomFetchDTO;
import ir.stream.app.entity.Room;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends AbstractCrudRepository<Room, Long> {

    @Query("select room.UUID from Room room where room.name=:name")
    Optional<String> findRoomUUIDByName(String name);

    long countRoomByUUID(String uuid);

    long countRoomByUUIDAndOwnerUsername(String uuid, String OwnerUsername);

    @Query(
            "select new ir.stream.app.dto.RoomFetchDTO(room.name, user.name, room.memberCount) " +
            "from Room room " +
            "inner join room.owner user " +
            "where room.active=true " +
            "and room.privateRoom=false"
    )
    List<RoomFetchDTO> findPublicRooms();

}