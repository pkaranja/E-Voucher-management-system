package co.tz.qroo.zawadi.giftcard.rest;

import co.tz.qroo.zawadi.giftcard.model.GiftcardDTO;
import co.tz.qroo.zawadi.giftcard.service.GiftcardService;
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
@RequestMapping(value = "/api/giftcards", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftcardResource {

    private final GiftcardService giftcardService;

    public GiftcardResource(final GiftcardService giftcardService) {
        this.giftcardService = giftcardService;
    }

    @GetMapping
    public ResponseEntity<List<GiftcardDTO>> getAllGiftcards() {
        return ResponseEntity.ok(giftcardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftcardDTO> getGiftcard(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(giftcardService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createGiftcard(@RequestBody @Valid final GiftcardDTO giftcardDTO) {
        final UUID createdId = giftcardService.create(giftcardDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateGiftcard(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final GiftcardDTO giftcardDTO) {
        giftcardService.update(id, giftcardDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGiftcard(@PathVariable(name = "id") final UUID id) {
        giftcardService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
