package pan.artem.test.dto.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlbumDto(Integer userId,
                       @NotNull Integer id,
                       String title) {
}
