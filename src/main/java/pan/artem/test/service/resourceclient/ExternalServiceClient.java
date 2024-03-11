package pan.artem.test.service.resourceclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class ExternalServiceClient<E, C> {
    private final RestTemplate restTemplate;
    private final Class<E> clazz;
    private final Class<E[]> arrayClazz;

    public ExternalServiceClient(RestTemplate restTemplate, Class<E> clazz, Class<E[]> arrayClazz) {
        this.restTemplate = restTemplate;
        this.clazz = clazz;
        this.arrayClazz = arrayClazz;
    }

    public List<E> getAll() {
        var response = restTemplate.getForObject(
                "/",
                arrayClazz
        );
        return List.of(Objects.requireNonNull(response));
    }

    public E create(C e) {
        return restTemplate.postForObject(
                "/",
                e,
                clazz
        );
    }

    public E get(int id) {
        return restTemplate.getForObject(
                "/{id}",
                clazz,
                id
        );
    }

    public E update(int id, C c) {
        var entity = new HttpEntity<>(c);
        return restTemplate.exchange(
                "/{id}",
                HttpMethod.PUT,
                entity,
                clazz,
                id
        ).getBody();
    }

    public void delete(int id) {
        restTemplate.delete(
                "/{id}",
                id
        );
    }
}
