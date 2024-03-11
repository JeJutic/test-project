package pan.artem.test.service.resourceclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pan.artem.test.dto.album.AlbumCreateDto;
import pan.artem.test.dto.album.AlbumDto;

@Service
public class AlbumClient extends ExternalServiceClient<AlbumDto, AlbumCreateDto> {
    public AlbumClient(
            @Qualifier("albumRestTemplate") RestTemplate restTemplate
    ) {
        super(restTemplate, AlbumDto.class, AlbumDto[].class);
    }
}
