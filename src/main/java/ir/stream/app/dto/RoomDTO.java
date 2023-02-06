package ir.stream.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String name;
    private boolean active;
    private MultipartFile image;
    private String imageName;
    private boolean privateRoom;
    private String privateCode;

    public RoomDTO(String name, boolean active, String imageName, boolean privateRoom, String privateCode) {
        this.name = name;
        this.active = active;
        this.imageName = imageName;
        this.privateRoom = privateRoom;
        this.privateCode = privateCode;
    }
}
