package pan.artem.test.entity.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "websocket_audit")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String session;
    private String principal;
    private String status;

    private Instant timestamp;
}
