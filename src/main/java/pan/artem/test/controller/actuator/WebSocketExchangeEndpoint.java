package pan.artem.test.controller.actuator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import pan.artem.test.entity.audit.WebSocketAudit;
import pan.artem.test.repository.audit.WebSocketAuditRepository;

import java.util.List;

@Component
@Endpoint(id = "websocketexchanges")
@AllArgsConstructor
public class WebSocketExchangeEndpoint {
    private final WebSocketAuditRepository auditRepository;

    @ReadOperation
    public List<WebSocketAudit> exchanges() {
        return auditRepository.findAll();
    }
}
