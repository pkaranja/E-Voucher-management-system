package co.tz.qroo.zawadi.issuer.model;

import co.tz.qroo.zawadi.user.model.ActiveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssuerDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    @IssuerNameUnique
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String website;

    @Size(max = 255)
    private String address;

    @NotNull
    private Long hits;

    @NotNull
    @Size(max = 255)
    private String logo;

    @NotNull
    @Size(max = 255)
    private String banner;

    @Size(max = 255)
    private String facebook;

    @Size(max = 255)
    private String instagram;

    @Size(max = 7)
    private String primaryColor;

    @Size(max = 7)
    private String secondaryColor;

    @Size(max = 7)
    private String primaryFontColor;

    @Size(max = 7)
    private String secondaryFontColor;

    private String description;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "30.08")
    private BigDecimal minAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "74.08")
    private BigDecimal maxAmount;

    @NotNull
    private Boolean popular;

    @NotNull
    private Boolean featured;

    @NotNull
    private ActiveStatus status;

    private List<UUID> category;

}