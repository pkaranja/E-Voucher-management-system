package co.tz.qroo.zawadi.payment_method.model;

import co.tz.qroo.zawadi.user.model.ActiveStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentMethodDTO {

    private UUID id;

    @NotNull
    @Size(max = 20)
    private String provider;

    @NotNull
    @Size(max = 255)
    private String paymentNumber;

    @NotNull
    private ActiveStatus status;

    @NotNull
    private UUID branch;

}
