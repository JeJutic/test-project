package pan.artem.test.repository.audit;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import pan.artem.test.ApplicationUtil;
import pan.artem.test.entity.audit.HttpAudit;
import pan.artem.test.exception.DataSourceException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PersistentTraceRepository implements HttpExchangeRepository {
    private final HttpAuditRepository requestRepository;

    private HttpExchange convert(HttpAudit entity) {
        URI uri;
        try {
            uri = new URI(entity.getUri());
        } catch (URISyntaxException e) {
            throw new DataSourceException(e);
        }

        return new HttpExchange(
                entity.getTimestamp(),
                new HttpExchange.Request(uri, null, entity.getMethod(), Map.of()),
                new HttpExchange.Response(entity.getStatus(), Map.of()),
                new HttpExchange.Principal(entity.getPrincipal()),
                new HttpExchange.Session(null),
                null
        );
    }

    private HttpAudit convert(HttpExchange exchange) {
        // exchange.getPrincipal() is null for some reason, maybe because of some filter order
        return new HttpAudit(
                null,
                exchange.getRequest().getMethod(),
                exchange.getRequest().getUri().toString(),
                ApplicationUtil.getPrincipalName(
                        SecurityContextHolder.getContext().getAuthentication()
                ),
                exchange.getResponse().getStatus(),
                exchange.getTimestamp()
        );
    }

    @Override
    public List<HttpExchange> findAll() {
        return requestRepository.findAll().stream()
                .map(this::convert).toList();
    }

    @Override
    public void add(HttpExchange httpExchange) {
        requestRepository.save(convert(httpExchange));
    }
}
