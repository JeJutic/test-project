package pan.artem.test.service.resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pan.artem.test.dto.album.AlbumCreateDto;
import pan.artem.test.dto.album.AlbumDto;
import pan.artem.test.service.resourceclient.AlbumClient;

import java.util.List;

@Service
public class AlbumServiceWrapper extends ResourceServiceWrapper<AlbumDto, AlbumCreateDto> {
    protected AlbumServiceWrapper(AlbumClient client) {
        super(client);
    }

    @Cacheable("albums")
    @Override
    public List<AlbumDto> getAll() {
        return super.getAll();
    }

    @Override
    public AlbumDto create(AlbumCreateDto e) {
        return super.create(e);
    }

    @Cacheable("album")
    @Override
    public AlbumDto get(int id) {
        return super.get(id);
    }

    @CachePut(value = "album", key = "#id")
    @Override
    public AlbumDto update(int id, AlbumCreateDto e) {
        return super.update(id, e);
    }

    @CacheEvict("album")
    @Override
    public void delete(int id) {
        super.delete(id);
    }
}
