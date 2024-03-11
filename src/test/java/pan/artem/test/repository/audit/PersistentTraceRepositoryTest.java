package pan.artem.test.repository.audit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import pan.artem.test.entity.audit.HttpAudit;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class PersistentTraceRepositoryTest {
    private HttpAuditRepository auditRepository;
    private PersistentTraceRepository traceRepository;

    private final HttpAudit audit1 = new HttpAudit(
            1L, "POST", "http://localhost:8080/api/albums", "admin", 201, Instant.ofEpochMilli(100)
    );
    private final HttpAudit audit2 = new HttpAudit(
            2L, "GET", "http://localhost:8080/api/albums", "john", 200, Instant.ofEpochMilli(200)
    );
    private final HttpExchange exchange = new HttpExchange(
            Instant.ofEpochMilli(100),
            new HttpExchange.Request(new URI("http://localhost:8080/api/albums"), null, "POST", Map.of()),
            new HttpExchange.Response(201, Map.of()),
            new HttpExchange.Principal("admin"),
            new HttpExchange.Session(null),
            null
    );

    PersistentTraceRepositoryTest() throws URISyntaxException {
    }

    @BeforeEach
    void init() {
        auditRepository = Mockito.mock(HttpAuditRepository.class);
        traceRepository = new PersistentTraceRepository(auditRepository);
    }

    @Test
    void findAll() {
        when(auditRepository.findAll()).thenReturn(List.of(
                audit1, audit2
        ));

        var list = traceRepository.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void add() {
        traceRepository.add(exchange);

        verify(auditRepository).save(any());
    }
}