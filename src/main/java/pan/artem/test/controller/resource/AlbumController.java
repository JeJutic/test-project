package pan.artem.test.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pan.artem.test.dto.album.AlbumCreateDto;
import pan.artem.test.dto.album.AlbumDto;
import pan.artem.test.service.resource.AlbumServiceWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController extends ResourceController<AlbumDto, AlbumCreateDto> {
    public AlbumController(AlbumServiceWrapper externalService) {
        super(externalService);
    }

    @Operation(summary = "Retrieves all albums")
    @GetMapping
    @Override
    public ResponseEntity<List<AlbumDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Creates new album",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New album created"
                    )
            }
    )
    @PostMapping
    @Override
    public ResponseEntity<AlbumDto> create(@Valid @RequestBody AlbumCreateDto albumCreateDto) {
        return super.create(albumCreateDto);
    }

    @Operation(
            summary = "Retrieves album with specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Album not found"
                    )
            }
    )
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<AlbumDto> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Operation(summary = "Updates album with specified id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<AlbumDto> put(@PathVariable("id") @NotNull Integer id,
                                        @Valid @RequestBody AlbumCreateDto albumCreateDto) {
        return super.put(id, albumCreateDto);
    }

    @Operation(summary = "Deletes album with specified id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull Integer id) {
        return super.delete(id);
    }
}
