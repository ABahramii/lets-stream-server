package ir.stream.app.entity;

import ir.stream.core.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHAT")
public class Chat extends AbstractBaseEntity<Long> {

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "TIME")
    private String time;

    @Column(name = "SENDER_NAME")
    private String senderName;

    @Column(name = "SENDER_IS_USER")
    private boolean senderIsUser;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

}
