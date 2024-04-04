package co.tz.qroo.zawadi.audit.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuditDTO {

    private UUID id;

    @NotNull
    private AuditAction action;

    @Size(max = 255)
    private String details;

    @NotNull
    @AuditUserUnique
    private UUID user;

}
