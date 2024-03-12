package pan.artem.test.controller.echo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pan.artem.test.ApplicationUtil;
import pan.artem.test.entity.audit.WebSocketAudit;
import pan.artem.test.exception.websocket.WaitResponseException;
import pan.artem.test.repository.audit.WebSocketAuditRepository;
import pan.artem.test.service.echo.EchoService;

import java.io.IOException;
import java.time.Instant;

@Controller
@AllArgsConstructor
public class EchoHandler extends AbstractWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EchoService echoService;
    private final WebSocketAuditRepository auditRepository;

    private void audit(WebSocketSession session, String status) {
        // Skipped service layer for audit
        auditRepository.save(new WebSocketAudit(
                null,
                session.getId(),
                ApplicationUtil.getPrincipalName(session.getPrincipal()),
                status,
                Instant.now()
        ));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        audit(session, "established");
        echoService.afterConnectionEstablished(session.getId());
    }

    @Override
    public void handleMessage(
            WebSocketSession session, WebSocketMessage<?> message
    ) throws IOException {
        try {
            var response = echoService.handleMessage(session.getId(), message);
            session.sendMessage(response);
        } catch (WaitResponseException e) {
            logger.warn("Couldn't get response from echo server on {} session", session, e);

            session.sendMessage(new TextMessage("Error: please try again later"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        var code = status.getCode();
        if (code != 1000) {
            logger.info("Session {} closed with code {}: {}", session, code, status.getReason());
        }
        audit(session, "closed " + code);
    }
}
