package pan.artem.test.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeoDto(@JsonProperty("lat") Double latitude,
                     @JsonProperty("lng") Double longitude) {
}
