package ir.stream.app.repository;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.RoomUser;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomUserRepository extends AbstractCrudRepository<RoomUser, Long> {

    @Query(
            "select new ir.stream.app.dto.MemberDTO(user.username, true) from RoomUser roomUser " +
            "inner join roomUser.room room " +
            "inner join roomUser.user user " +
            "where room.UUID=:uuid"
    )
    List<MemberDTO> findUserMemberDtoListByRoomUUID(String uuid);

    @Query(
            "select count(roomUser) from RoomUser roomUser " +
            "inner join roomUser.room room " +
            "inner join roomUser.user user " +
            "where room.UUID=:roomUUID and user.username=:username"
    )
    long countByUsernameAndRoomUUID(String username, String roomUUID);

    @Query(
            "select roomUser from RoomUser roomUser " +
            "inner join roomUser.room room " +
            "inner join roomUser.user user " +
            "where room.UUID=:roomUUID and user.username=:username"
    )
    RoomUser findByUsernameAndRoomUUID(String username, String roomUUID);
}
