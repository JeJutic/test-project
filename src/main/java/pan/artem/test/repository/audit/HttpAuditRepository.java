package pan.artem.test.repository.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import pan.artem.test.entity.audit.HttpAudit;

public interface HttpAuditRepository extends JpaRepository<HttpAudit, Long> {
}
