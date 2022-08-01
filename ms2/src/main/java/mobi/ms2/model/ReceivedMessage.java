package mobi.ms2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mobi.ms2.enums.Ms;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedMessage {
    private String id;
    private String reachingMsg;
    private Ms source;
    private Ms dst;
}
