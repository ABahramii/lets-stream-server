package ir.stream.app.repository;

import ir.stream.app.dto.MemberDTO;
import ir.stream.app.entity.Guest;
import ir.stream.core.repository.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuestRepository extends AbstractCrudRepository<Guest, Long> {

    @Query(
            "select new ir.stream.app.dto.MemberDTO(guest.name, false) from Guest guest " +
            "inner join guest.room room " +
            "where room.UUID=:uuid"
    )
    List<MemberDTO> findGuestMemberDtoListByRoomUUID(String uuid);


    long countGuestByNameAndRoomUUID(String name, String roomUUID);

    Guest findByNameAndRoomUUID(String name, String roomUUID);

}
