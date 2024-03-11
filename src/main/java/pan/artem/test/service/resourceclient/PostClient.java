package pan.artem.test.service.resourceclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pan.artem.test.dto.post.PostCreateDto;
import pan.artem.test.dto.post.PostDto;

@Service
public class PostClient extends ExternalServiceClient<PostDto, PostCreateDto> {
    public PostClient(
            @Qualifier("postRestTemplate") RestTemplate restTemplate
    ) {
        super(restTemplate, PostDto.class, PostDto[].class);
    }
}
