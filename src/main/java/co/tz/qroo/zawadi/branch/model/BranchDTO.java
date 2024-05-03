package co.tz.qroo.zawadi.branch.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BranchDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String phoneNumber;

    @Size(max = 255)
    private String emailAddress;

    @Size(max = 255)
    private String contactPersonName;

    @NotNull
    private UUID issuer;

}
