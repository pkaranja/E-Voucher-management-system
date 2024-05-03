package co.tz.qroo.zawadi.giftcard.service;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.model.GiftcardDTO;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.issuer.repos.IssuerRepository;
import co.tz.qroo.zawadi.theme.domain.Theme;
import co.tz.qroo.zawadi.theme.repos.ThemeRepository;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.transaction.repos.TransactionRepository;
import co.tz.qroo.zawadi.user.domain.User;
import co.tz.qroo.zawadi.user.repos.UserRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class GiftcardService {

    private final GiftcardRepository giftcardRepository;
    private final IssuerRepository issuerRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final TransactionRepository transactionRepository;

    public GiftcardService(final GiftcardRepository giftcardRepository,
                           final IssuerRepository issuerRepository, final UserRepository userRepository,
                           final ThemeRepository themeRepository,
                           final TransactionRepository transactionRepository) {
        this.giftcardRepository = giftcardRepository;
        this.issuerRepository = issuerRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<GiftcardDTO> findAll() {
        final List<Giftcard> giftcards = giftcardRepository.findAll(Sort.by("id"));
        return giftcards.stream()
                .map(giftcard -> mapToDTO(giftcard, new GiftcardDTO()))
                .toList();
    }

    public GiftcardDTO get(final UUID id) {
        return giftcardRepository.findById(id)
                .map(giftcard -> mapToDTO(giftcard, new GiftcardDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final GiftcardDTO giftcardDTO) {
        final Giftcard giftcard = new Giftcard();
        mapToEntity(giftcardDTO, giftcard);
        return giftcardRepository.save(giftcard).getId();
    }

    public void update(final UUID id, final GiftcardDTO giftcardDTO) {
        final Giftcard giftcard = giftcardRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(giftcardDTO, giftcard);
        giftcardRepository.save(giftcard);
    }

    public void delete(final UUID id) {
        giftcardRepository.deleteById(id);
    }

    private GiftcardDTO mapToDTO(final Giftcard giftcard, final GiftcardDTO giftcardDTO) {
        giftcardDTO.setId(giftcard.getId());
        giftcardDTO.setCode(giftcard.getCode());
        giftcardDTO.setCvv(giftcard.getCvv());
        giftcardDTO.setValue(giftcard.getValue());
        giftcardDTO.setExpirationDate(giftcard.getExpirationDate());
        giftcardDTO.setMessage(giftcard.getMessage());
        giftcardDTO.setPurchaserName(giftcard.getPurchaserName());
        giftcardDTO.setRecipientPhoneNumber(giftcard.getRecipientPhoneNumber());
        giftcardDTO.setReceipientName(giftcard.getReceipientName());
        giftcardDTO.setStatus(giftcard.getStatus());
        giftcardDTO.setTitle(giftcard.getTitle());
        giftcardDTO.setIssuers(giftcard.getIssuers().stream()
                .map(Issuer::getId)
                .toList());
        giftcardDTO.setPurchaser(giftcard.getPurchaser() == null ? null : giftcard.getPurchaser().getId());
        giftcardDTO.setRecipient(giftcard.getRecipient() == null ? null : giftcard.getRecipient().getId());
        giftcardDTO.setTheme(giftcard.getTheme() == null ? null : giftcard.getTheme().getId());
        return giftcardDTO;
    }

    private Giftcard mapToEntity(final GiftcardDTO giftcardDTO, final Giftcard giftcard) {
        giftcard.setCode(giftcardDTO.getCode());
        giftcard.setCvv(giftcardDTO.getCvv());
        giftcard.setValue(giftcardDTO.getValue());
        giftcard.setExpirationDate(giftcardDTO.getExpirationDate());
        giftcard.setMessage(giftcardDTO.getMessage());
        giftcard.setPurchaserName(giftcardDTO.getPurchaserName());
        giftcard.setRecipientPhoneNumber(giftcardDTO.getRecipientPhoneNumber());
        giftcard.setReceipientName(giftcardDTO.getReceipientName());
        giftcard.setStatus(giftcardDTO.getStatus());
        giftcard.setTitle(giftcardDTO.getTitle());
        final List<Issuer> issuers = issuerRepository.findAllById(
                giftcardDTO.getIssuers() == null ? Collections.emptyList() : giftcardDTO.getIssuers());
        if (issuers.size() != (giftcardDTO.getIssuers() == null ? 0 : giftcardDTO.getIssuers().size())) {
            throw new NotFoundException("one of issuers not found");
        }
        giftcard.setIssuers(new HashSet<>(issuers));
        final User purchaser = giftcardDTO.getPurchaser() == null ? null : userRepository.findById(giftcardDTO.getPurchaser())
                .orElseThrow(() -> new NotFoundException("purchaser not found"));
        giftcard.setPurchaser(purchaser);
        final User recipient = giftcardDTO.getRecipient() == null ? null : userRepository.findById(giftcardDTO.getRecipient())
                .orElseThrow(() -> new NotFoundException("recipient not found"));
        giftcard.setRecipient(recipient);
        final Theme theme = giftcardDTO.getTheme() == null ? null : themeRepository.findById(giftcardDTO.getTheme())
                .orElseThrow(() -> new NotFoundException("theme not found"));
        giftcard.setTheme(theme);
        return giftcard;
    }

    public boolean codeExists(final String code) {
        return giftcardRepository.existsByCodeIgnoreCase(code);
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Giftcard giftcard = giftcardRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Transaction giftcardTransaction = transactionRepository.findFirstByGiftcard(giftcard);
        if (giftcardTransaction != null) {
            referencedWarning.setKey("giftcard.transaction.giftcard.referenced");
            referencedWarning.addParam(giftcardTransaction.getId());
            return referencedWarning;
        }
        return null;
    }

}