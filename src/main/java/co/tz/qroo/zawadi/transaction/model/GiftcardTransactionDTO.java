package co.tz.qroo.zawadi.transaction.model;

import co.tz.qroo.zawadi.model.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class GiftcardTransactionDTO {
    @NotNull
    private UUID issuerId;

    @NotNull
    private UUID purchaserId;

    @NotNull
    @Size(max = 255)
    private String recipient;

    @NotNull
    //TODO: Convert to UUID later
    //private UUID giftcardTheme;
    private int giftcardTheme;

    @NotNull
    @Size(max = 100)
    private String giftcartTitle;

    @NotNull
    @Size(max = 255)
    private String giftCardMessage;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "92.08")
    private BigDecimal transactionAmount;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "92.08")
    private BigDecimal giftCardValue;

    @NotNull
    @Size(max = 15)
    private String transactionPhoneNumber;

    @NotNull
    private String provider;

    @NotNull
    @Size(max = 25)
    private String currency;

    private String deviceFingerprint;

    private String clientIp;

    private String narrative;

    @NotNull
    private PaymentType paymentType;

}
