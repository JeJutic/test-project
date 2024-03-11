package pan.artem.test.controller.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pan.artem.test.service.resource.ResourceServiceWrapper;

import java.util.List;

@AllArgsConstructor
public abstract class ResourceController<E, C> {
    private final ResourceServiceWrapper<E, C> externalService;

    public ResponseEntity<List<E>> getAll() {
        var list = externalService.getAll();
        return ResponseEntity.ok().body(list);
    }

    public ResponseEntity<E> create(C c) {
        var entity = externalService.create(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    public ResponseEntity<E> get(Integer id) {
        var entity = externalService.get(id);
        return ResponseEntity.ok().body(entity);
    }

    public ResponseEntity<E> put(Integer id, C c) {
        var entity = externalService.update(id, c);
        return ResponseEntity.ok().body(entity);
    }

    public ResponseEntity<?> delete(Integer id) {
        externalService.delete(id);
        return ResponseEntity.ok().build();
    }
}
