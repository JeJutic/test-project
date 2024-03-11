package pan.artem.test.service.resourceclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pan.artem.test.dto.user.UserCreateDto;
import pan.artem.test.dto.user.UserDto;

@Service
public class UserClient extends ExternalServiceClient<UserDto, UserCreateDto> {
    public UserClient(
            @Qualifier("userRestTemplate") RestTemplate restTemplate
    ) {
        super(restTemplate, UserDto.class, UserDto[].class);
    }
}
