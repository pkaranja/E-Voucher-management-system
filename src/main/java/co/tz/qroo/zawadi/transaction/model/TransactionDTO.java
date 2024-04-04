package co.tz.qroo.zawadi.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private UUID id;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "92.08")
    private BigDecimal amount;

    @NotNull
    @Size(max = 255)
    private String paymentMethod;

    @NotNull
    @Size(max = 255)
    private String externalId;

    @NotNull
    @Size(max = 255)
    private String provider;

    @Size(max = 255)
    private String status;

}
