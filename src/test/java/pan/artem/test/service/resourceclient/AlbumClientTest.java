package pan.artem.test.service.resourceclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pan.artem.test.dto.album.AlbumCreateDto;
import pan.artem.test.dto.album.AlbumDto;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlbumClientTest {
    private RestTemplate restTemplate;
    private AlbumClient albumClient;

    private final AlbumCreateDto album1 = new AlbumCreateDto(1, "title1");

    @BeforeEach
    void init() {
        restTemplate = Mockito.mock(RestTemplate.class);
        albumClient = new AlbumClient(restTemplate);
    }

    @Test
    void getAll() {
        when(restTemplate.getForObject(any(String.class), any()))
                .thenReturn(new AlbumDto[]{});

        albumClient.getAll();

        verify(restTemplate).getForObject(
                "/",
                AlbumDto[].class
        );
    }

    @Test
    void create() {
        albumClient.create(album1);

        verify(restTemplate).postForObject(
                "/",
                album1,
                AlbumDto.class
        );
    }

    @Test
    void get() {
        albumClient.get(1);

        verify(restTemplate).getForObject(
                "/{id}",
                AlbumDto.class,
                1
        );
    }

    @Test
    void update() {
        when(restTemplate.exchange(any(), any(), any(), eq(AlbumDto.class), anyInt()))
                .thenReturn(ResponseEntity.ok(null));

        albumClient.update(42, album1);

        verify(restTemplate).exchange(
                "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(album1),
                AlbumDto.class,
                42
        );
    }

    @Test
    void delete() {
        albumClient.delete(2);

        verify(restTemplate).delete(
                "/{id}",
                2
        );
    }
}