package pan.artem.test.dto;

import lombok.Value;

@Value
public record ErrorInfo(String url, String error) {
}
