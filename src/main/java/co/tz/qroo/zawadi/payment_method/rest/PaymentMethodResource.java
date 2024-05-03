package co.tz.qroo.zawadi.payment_method.rest;

import co.tz.qroo.zawadi.payment_method.model.PaymentMethodDTO;
import co.tz.qroo.zawadi.payment_method.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/paymentMethods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodResource {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodResource(final PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods() {
        return ResponseEntity.ok(paymentMethodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(
            @PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(paymentMethodService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createPaymentMethod(
            @RequestBody @Valid final PaymentMethodDTO paymentMethodDTO) {
        final UUID createdId = paymentMethodService.create(paymentMethodDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updatePaymentMethod(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final PaymentMethodDTO paymentMethodDTO) {
        paymentMethodService.update(id, paymentMethodDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable(name = "id") final UUID id) {
        paymentMethodService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
