package co.tz.qroo.zawadi.issuer.rest;

import co.tz.qroo.zawadi.issuer.model.IssuerDTO;
import co.tz.qroo.zawadi.issuer.service.IssuerService;
import co.tz.qroo.zawadi.util.ReferencedException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import co.tz.qroo.zawadi.util.RestApiFilter.SearchRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
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
@RequestMapping(value = "/api/issuers", produces = MediaType.APPLICATION_JSON_VALUE)
public class IssuerResource {

    private final IssuerService issuerService;

    public IssuerResource(final IssuerService issuerService) {
        this.issuerService = issuerService;
    }

    @PostMapping
    public Page<IssuerDTO> searchIssuers(@RequestBody SearchRequest request) { return issuerService.searchAll(request); }

    @GetMapping("/{id}")
    public ResponseEntity<IssuerDTO> getIssuer(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(issuerService.get(id));
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createIssuer(@RequestBody @Valid final IssuerDTO issuerDTO) {
        final UUID createdId = issuerService.create(issuerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateIssuer(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final IssuerDTO issuerDTO) {
        issuerService.update(id, issuerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteIssuer(@PathVariable(name = "id") final UUID id) {
        final ReferencedWarning referencedWarning = issuerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        issuerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
