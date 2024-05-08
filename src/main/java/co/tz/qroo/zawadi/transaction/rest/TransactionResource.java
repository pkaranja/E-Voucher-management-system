package co.tz.qroo.zawadi.transaction.rest;

import co.tz.qroo.zawadi.transaction.model.GiftcardTransactionDTO;
import co.tz.qroo.zawadi.transaction.model.GiftcardTransactionResponseDTO;
import co.tz.qroo.zawadi.transaction.model.TransactionDTO;
import co.tz.qroo.zawadi.transaction.service.TransactionService;
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
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(transactionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<GiftcardTransactionResponseDTO> createGiftcardTransaction( @RequestBody @Valid final GiftcardTransactionDTO giftcardTransactionDTO) {
        final GiftcardTransactionResponseDTO transactionResponse = transactionService.createGiftcardTransaction(giftcardTransactionDTO);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createTransaction(
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        final UUID createdId = transactionService.create(transactionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTransaction(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        transactionService.update(id, transactionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaction(@PathVariable(name = "id") final UUID id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
