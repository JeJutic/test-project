package pan.artem.test.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CompanyDto(String name,
                         String catchPhrase,
                         @JsonProperty("bs") String balanceSheet,
                         String body) {
}
