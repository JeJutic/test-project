package pan.artem.test.dto.user;

public record UserCreateDto(String name,
                            String username,
                            AddressDto address,
                            String phone,
                            String website,
                            CompanyDto company) {
}
