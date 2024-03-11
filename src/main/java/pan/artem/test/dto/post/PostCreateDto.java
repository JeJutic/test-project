package pan.artem.test.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostCreateDto(Integer userId,
                            String title,
                            String body) {
}
