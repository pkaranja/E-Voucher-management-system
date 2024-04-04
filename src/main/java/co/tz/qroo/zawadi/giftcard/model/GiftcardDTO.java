package co.tz.qroo.zawadi.giftcard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GiftcardDTO {

    private UUID id;

    @NotNull
    @Size(max = 12)
    @GiftcardCodeUnique
    private String code;

    @NotNull
    private Integer cvv;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "25.08")
    private BigDecimal value;

    @NotNull
    private LocalDate expirationDate;

    private String message;

    @NotNull
    @Size(max = 255)
    private String purchaserName;

    @NotNull
    @Size(max = 255)
    private String receipientName;

    @NotNull
    private GiftcardStatus status;

    @NotNull
    private UUID issuer;

    @NotNull
    private UUID purchaser;

    @NotNull
    private UUID recipient;

    @GiftcardThemeUnique
    private UUID theme;

    @NotNull
    @GiftcardTransactionUnique
    private UUID transaction;

}
