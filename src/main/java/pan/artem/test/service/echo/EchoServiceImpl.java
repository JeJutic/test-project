package pan.artem.test.service.echo;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pan.artem.test.exception.websocket.WaitResponseException;
import pan.artem.test.properties.AppProperties;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Service
public class EchoServiceImpl implements EchoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WebSocketClient client;
    private final String serviceUrl;
    private final long waitTimeout;

    private final Map<String, ClientWebSocketHandler> proxySessionHandler = new ConcurrentHashMap<>();

    public EchoServiceImpl(WebSocketClient client, AppProperties.EchoServiceProperties properties) {
        this.client = client;
        this.serviceUrl = properties.getUrl();
        this.waitTimeout = properties.getWaitTimeout();
    }

    private static class ClientWebSocketHandler extends AbstractWebSocketHandler {
        private WebSocketSession session;
        @Setter
        private Consumer<WebSocketMessage<?>> onRecieve;

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            this.session = session;
        }

        public void sendMessage(WebSocketMessage<?> message) throws IOException {
            if (session == null) {
                throw new IOException("Connection wasn't established");
            }
            session.sendMessage(message);
        }

        // This class isn't protected against concurrent access as long as
        // WebSocketHandler#handleMessage for each connection called in the same thread
        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
            if (onRecieve != null) {
                onRecieve.accept(message);
                onRecieve = null;
            }
        }
    }

    @Override
    public void afterConnectionEstablished(String sessionId) {
        logger.info("Connection for session {} established", sessionId);

        var handler = new ClientWebSocketHandler();
        client.execute(handler, serviceUrl);
        proxySessionHandler.put(sessionId, handler);
    }

    private CompletableFuture<WebSocketMessage<?>> innerHandleMessage(
            String sessionId, WebSocketMessage<?> message
    ) throws IOException {
        var handler = proxySessionHandler.get(sessionId);

        final CompletableFuture<WebSocketMessage<?>> completableFuture = new CompletableFuture<>();
        handler.setOnRecieve(completableFuture::complete);
        handler.sendMessage(message);

        return completableFuture;
    }

    @Override
    public WebSocketMessage<?> handleMessage(
            String sessionId, WebSocketMessage<?> message
    ) throws IOException, WaitResponseException {
        logger.debug("Received {} on session {}", message, sessionId);

        try {
            return innerHandleMessage(sessionId, message)
                    .get(waitTimeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new WaitResponseException(e);
        }
    }
}
