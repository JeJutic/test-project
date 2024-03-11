package pan.artem.test.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDto(Integer userId,
                      @NotNull Integer id,
                      String title,
                      String body) {
}
