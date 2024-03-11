package pan.artem.test.service.resource;

import pan.artem.test.service.resourceclient.ExternalServiceClient;

import java.util.List;

public abstract class ResourceServiceWrapper<E, C> {
    private final ExternalServiceClient<E, C> client;

    protected ResourceServiceWrapper(ExternalServiceClient<E, C> client) {
        this.client = client;
    }

    public List<E> getAll() {
        return client.getAll();
    }

    public E create(C e) {
        return client.create(e);
    }

    public E get(int id) {
        return client.get(id);
    }

    public E update(int id, C c) {
        return client.update(id, c);
    }

    public void delete(int id) {
        client.delete(id);
    }
}
