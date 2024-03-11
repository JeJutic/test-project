package pan.artem.test.controller.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pan.artem.test.dto.album.AlbumCreateDto;
import pan.artem.test.dto.album.AlbumDto;
import pan.artem.test.service.resource.AlbumServiceWrapper;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class AlbumControllerTest {
    private AlbumServiceWrapper serviceWrapper;
    private AlbumController controller;

    private final AlbumDto album1 = new AlbumDto(1, 1, "title1");
    private final AlbumDto album2 = new AlbumDto(1, 2, "title2");
    private final AlbumCreateDto albumCreate1 = new AlbumCreateDto(1, "title1");
    private final AlbumCreateDto albumCreate2 = new AlbumCreateDto(2, "title2");

    @BeforeEach
    void init() {
        serviceWrapper = Mockito.mock(AlbumServiceWrapper.class);
        controller = new AlbumController(serviceWrapper);
    }

    @Test
    void getAll() {
        when(serviceWrapper.getAll()).thenReturn(List.of(
                album1, album2
        ));

        var response = controller.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertTrue(Objects.requireNonNull(response.getBody()).contains(album1));
    }

    @Test
    void create() {
        when(serviceWrapper.create(albumCreate1)).thenReturn(album1);

        var response = controller.create(albumCreate1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(album1, response.getBody());
    }

    @Test
    void get() {
        when(serviceWrapper.get(1)).thenReturn(album1);

        var response = controller.get(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(album1, response.getBody());
    }

    @Test
    void put() {
        when(serviceWrapper.update(1, albumCreate2)).thenReturn(album2);

        var response = controller.put(1, albumCreate2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(album2, response.getBody());
    }

    @Test
    void delete() {
        var response = controller.delete(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(serviceWrapper).delete(1);
    }
}