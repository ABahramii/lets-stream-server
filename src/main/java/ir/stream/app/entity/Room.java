package ir.stream.app.entity;

import ir.stream.core.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOM")
public class Room extends AbstractBaseEntity<Long> {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER")
    private User owner;

    @OneToMany(mappedBy = "room")
    private Set<RoomUser> users;

    @OneToMany(mappedBy = "room")
    private Set<Guest> guests;
}
