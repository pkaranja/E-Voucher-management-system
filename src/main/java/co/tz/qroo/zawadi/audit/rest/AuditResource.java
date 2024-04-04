package co.tz.qroo.zawadi.audit.rest;

import co.tz.qroo.zawadi.audit.model.AuditDTO;
import co.tz.qroo.zawadi.audit.service.AuditService;
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
@RequestMapping(value = "/api/audits", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditResource {

    private final AuditService auditService;

    public AuditResource(final AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<AuditDTO>> getAllAudits() {
        return ResponseEntity.ok(auditService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditDTO> getAudit(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(auditService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createAudit(@RequestBody @Valid final AuditDTO auditDTO) {
        final UUID createdId = auditService.create(auditDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateAudit(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final AuditDTO auditDTO) {
        auditService.update(id, auditDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAudit(@PathVariable(name = "id") final UUID id) {
        auditService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
