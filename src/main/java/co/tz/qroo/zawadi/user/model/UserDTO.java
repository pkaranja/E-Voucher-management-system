package co.tz.qroo.zawadi.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Size(max = 255)
    @UserExternalIdUnique
    private String externalId;

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

    @NotNull
    private Integer age;

    @Size(max = 255)
    private String location;

    private Gender gender;

    @Size(max = 255)
    private String nationality;

    @Size(max = 255)
    private String govtId;

    private LocalDate govtIdExpiryDate;

    @Size(max = 255)
    private String govtIdType;

    @Size(max = 255)
    private String region;

    @NotNull
    private Boolean privacyPolicyConsent;

    @NotNull
    private LocalDate privacyPolicyConsentDate;

    @NotNull
    private Boolean termsAndConditionConsent;

    @NotNull
    private LocalDate termsAndConditionConsentDate;

    @JsonProperty("isAutopayOn")
    private Boolean isAutopayOn;

    private Boolean phoneNumberValidated;

}