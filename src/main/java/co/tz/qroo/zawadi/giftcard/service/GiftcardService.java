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
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
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
        giftcardDTO.setReceipientName(giftcard.getReceipientName());
        giftcardDTO.setStatus(giftcard.getStatus());
        giftcardDTO.setIssuer(giftcard.getIssuer() == null ? null : giftcard.getIssuer().getId());
        giftcardDTO.setPurchaser(giftcard.getPurchaser() == null ? null : giftcard.getPurchaser().getId());
        giftcardDTO.setRecipient(giftcard.getRecipient() == null ? null : giftcard.getRecipient().getId());
        giftcardDTO.setTheme(giftcard.getTheme() == null ? null : giftcard.getTheme().getId());
        giftcardDTO.setTransaction(giftcard.getTransaction() == null ? null : giftcard.getTransaction().getId());
        return giftcardDTO;
    }

    private Giftcard mapToEntity(final GiftcardDTO giftcardDTO, final Giftcard giftcard) {
        giftcard.setCode(giftcardDTO.getCode());
        giftcard.setCvv(giftcardDTO.getCvv());
        giftcard.setValue(giftcardDTO.getValue());
        giftcard.setExpirationDate(giftcardDTO.getExpirationDate());
        giftcard.setMessage(giftcardDTO.getMessage());
        giftcard.setPurchaserName(giftcardDTO.getPurchaserName());
        giftcard.setReceipientName(giftcardDTO.getReceipientName());
        giftcard.setStatus(giftcardDTO.getStatus());
        final Issuer issuer = giftcardDTO.getIssuer() == null ? null : issuerRepository.findById(giftcardDTO.getIssuer())
                .orElseThrow(() -> new NotFoundException("issuer not found"));
        giftcard.setIssuer(issuer);
        final User purchaser = giftcardDTO.getPurchaser() == null ? null : userRepository.findById(giftcardDTO.getPurchaser())
                .orElseThrow(() -> new NotFoundException("purchaser not found"));
        giftcard.setPurchaser(purchaser);
        final User recipient = giftcardDTO.getRecipient() == null ? null : userRepository.findById(giftcardDTO.getRecipient())
                .orElseThrow(() -> new NotFoundException("recipient not found"));
        giftcard.setRecipient(recipient);
        final Theme theme = giftcardDTO.getTheme() == null ? null : themeRepository.findById(giftcardDTO.getTheme())
                .orElseThrow(() -> new NotFoundException("theme not found"));
        giftcard.setTheme(theme);
        final Transaction transaction = giftcardDTO.getTransaction() == null ? null : transactionRepository.findById(giftcardDTO.getTransaction())
                .orElseThrow(() -> new NotFoundException("transaction not found"));
        giftcard.setTransaction(transaction);
        return giftcard;
    }

    public boolean codeExists(final String code) {
        return giftcardRepository.existsByCodeIgnoreCase(code);
    }

    public boolean themeExists(final UUID id) {
        return giftcardRepository.existsByThemeId(id);
    }

    public boolean transactionExists(final UUID id) {
        return giftcardRepository.existsByTransactionId(id);
    }

}
