package pan.artem.test.service.resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pan.artem.test.dto.user.UserCreateDto;
import pan.artem.test.dto.user.UserDto;
import pan.artem.test.service.resourceclient.UserClient;

import java.util.List;

@Service
public class UserServiceWrapper extends ResourceServiceWrapper<UserDto, UserCreateDto> {
    protected UserServiceWrapper(UserClient client) {
        super(client);
    }

    @Cacheable("users")
    @Override
    public List<UserDto> getAll() {
        return super.getAll();
    }

    @Override
    public UserDto create(UserCreateDto e) {
        return super.create(e);
    }

    @Cacheable("user")
    @Override
    public UserDto get(int id) {
        return super.get(id);
    }

    @CachePut(value = "user", key = "#id")
    @Override
    public UserDto update(int id, UserCreateDto e) {
        return super.update(id, e);
    }

    @CacheEvict("user")
    @Override
    public void delete(int id) {
        super.delete(id);
    }
}
