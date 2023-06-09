package eventstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private long id;
    private String source;
    private LocalDateTime time;
    private String message;
}
