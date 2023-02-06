package ir.stream.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomFetchDTO {
    private String uuid;
    private boolean active;
    private String name;
    private String owner;
    private long memberCount;

    public RoomFetchDTO(String uuid, String name, String owner, long memberCount) {
        this.uuid = uuid;
        this.name = name;
        this.owner = owner;
        this.memberCount = memberCount;
    }
}
