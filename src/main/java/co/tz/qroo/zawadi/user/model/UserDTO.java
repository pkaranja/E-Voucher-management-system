package co.tz.qroo.zawadi.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private UUID id;

    @NotNull
    @UserExternalIdUnique
    private UUID externalId;

    @Size(max = 255)
    private String address;

    @NotNull
    private LocalDateTime lastLogin;

    @NotNull
    @Size(max = 15)
    @UserPhoneNumberUnique
    private String phoneNumber;

    @NotNull
    private Long giftcardsPurchased;

    @NotNull
    private Long giftcardsReceived;

    @NotNull
    private ActiveStatus status;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

}
