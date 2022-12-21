package ir.stream.entity;


import ir.stream.core.model.AbstractBaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "REFRESH_TOKEN")
public class RefreshToken extends AbstractBaseEntity<Long> {

    @Column(name = "EXPIRE_AT", nullable = false)
    private Instant expireAt;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true, nullable = false)
    private User user;

}
