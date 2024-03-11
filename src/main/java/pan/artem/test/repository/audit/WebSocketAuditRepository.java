package pan.artem.test.repository.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.test.entity.audit.WebSocketAudit;

public interface WebSocketAuditRepository extends JpaRepository<WebSocketAudit, Long>  {
}
