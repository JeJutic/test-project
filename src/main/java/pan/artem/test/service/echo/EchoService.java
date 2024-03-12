package pan.artem.test.service.echo;

import org.springframework.web.socket.WebSocketMessage;
import pan.artem.test.exception.websocket.WaitResponseException;

import java.io.IOException;

public interface EchoService {
    void afterConnectionEstablished(String sessionId);

    WebSocketMessage<?> handleMessage(
            String sessionId, WebSocketMessage<?> message
    ) throws IOException, WaitResponseException;
}
