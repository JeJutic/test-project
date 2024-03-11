package pan.artem.test.service.echo;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import pan.artem.test.exception.websocket.WaitResponseException;

import java.io.IOException;

public interface EchoService {
    void afterConnectionEstablished(String sessionId);

    WebSocketMessage<?> handleTextMessage(
            String sessionId, TextMessage message
    ) throws IOException, WaitResponseException;
}
