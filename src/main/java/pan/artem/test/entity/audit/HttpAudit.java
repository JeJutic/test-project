package pan.artem.test.entity.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "http_audit")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;
    private String uri;
    private String principal;

    private Integer status;
    private Instant timestamp;
}
