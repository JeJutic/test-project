package pan.artem.test.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pan.artem.test.dto.post.PostCreateDto;
import pan.artem.test.dto.post.PostDto;
import pan.artem.test.service.resource.PostServiceWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController extends ResourceController<PostDto, PostCreateDto> {
    public PostController(PostServiceWrapper externalService) {
        super(externalService);
    }

    @Operation(summary = "Retrieves all posts")
    @GetMapping
    @Override
    public ResponseEntity<List<PostDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Creates new post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New post created"
                    )
            }
    )
    @PostMapping
    @Override
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostCreateDto postCreateDto) {
        return super.create(postCreateDto);
    }

    @Operation(
            summary = "Retrieves post with specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post not found"
                    )
            }
    )
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PostDto> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Operation(summary = "Updates post with specified id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<PostDto> put(@PathVariable("id") @NotNull Integer id,
                                        @Valid @RequestBody PostCreateDto postCreateDto) {
        return super.put(id, postCreateDto);
    }

    @Operation(summary = "Deletes post with specified id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull Integer id) {
        return super.delete(id);
    }
}
