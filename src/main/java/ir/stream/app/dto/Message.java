package ir.stream.app.dto;

import ir.stream.app.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private long id;
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;

}
