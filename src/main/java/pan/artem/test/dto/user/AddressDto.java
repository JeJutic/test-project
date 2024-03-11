package pan.artem.test.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressDto(String street,
                         String suite,
                         String city,
                         String zipcode,
                         GeoDto geo) {
}
