package co.tz.qroo.zawadi.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiftcardTransactionResponseDTO {
    private Boolean status;
    private int code;
    private String message;
}
