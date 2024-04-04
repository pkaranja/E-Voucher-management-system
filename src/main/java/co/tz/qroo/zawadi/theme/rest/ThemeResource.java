package co.tz.qroo.zawadi.theme.rest;

import co.tz.qroo.zawadi.theme.model.ThemeDTO;
import co.tz.qroo.zawadi.theme.service.ThemeService;
import co.tz.qroo.zawadi.util.ReferencedException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/themes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThemeResource {

    private final ThemeService themeService;

    public ThemeResource(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(themeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createTheme(@RequestBody @Valid final ThemeDTO themeDTO) {
        final UUID createdId = themeService.create(themeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTheme(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final ThemeDTO themeDTO) {
        themeService.update(id, themeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTheme(@PathVariable(name = "id") final UUID id) {
        final ReferencedWarning referencedWarning = themeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
