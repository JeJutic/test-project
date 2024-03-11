package pan.artem.test.service.resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pan.artem.test.dto.post.PostCreateDto;
import pan.artem.test.dto.post.PostDto;
import pan.artem.test.service.resourceclient.PostClient;

import java.util.List;

@Service
public class PostServiceWrapper extends ResourceServiceWrapper<PostDto, PostCreateDto> {
    protected PostServiceWrapper(PostClient client) {
        super(client);
    }

    @Cacheable("posts")
    @Override
    public List<PostDto> getAll() {
        return super.getAll();
    }

    @Override
    public PostDto create(PostCreateDto e) {
        return super.create(e);
    }

    @Cacheable("post")
    @Override
    public PostDto get(int id) {
        return super.get(id);
    }

    @CachePut(value = "post", key = "#id")
    @Override
    public PostDto update(int id, PostCreateDto e) {
        return super.update(id, e);
    }

    @CacheEvict("post")
    @Override
    public void delete(int id) {
        super.delete(id);
    }
}
