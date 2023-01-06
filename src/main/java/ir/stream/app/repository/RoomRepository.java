package ir.stream.app.repository;

import ir.stream.app.entity.Room;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends AbstractCrudRepository<Room, Long> {

    @Query("select room.UUID from Room room where room.name=:name")
    Optional<String> findRoomUUIDByName(String name);


    long countRoomByUUID(String uuid);

}