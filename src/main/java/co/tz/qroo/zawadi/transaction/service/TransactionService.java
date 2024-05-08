package co.tz.qroo.zawadi.transaction.service;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.model.GiftcardDTO;
import co.tz.qroo.zawadi.giftcard.model.GiftcardStatus;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.giftcard.service.GiftcardService;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.transaction.model.GiftcardTransactionDTO;
import co.tz.qroo.zawadi.transaction.model.GiftcardTransactionResponseDTO;
import co.tz.qroo.zawadi.transaction.model.TransactionDTO;
import co.tz.qroo.zawadi.transaction.repos.TransactionRepository;
import co.tz.qroo.zawadi.util.NotFoundException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final GiftcardRepository giftcardRepository;
    private final GiftcardService giftcardService;

    public TransactionService(final TransactionRepository transactionRepository,
            final GiftcardRepository giftcardRepository, final GiftcardService giftcardService) {
        this.transactionRepository = transactionRepository;
        this.giftcardRepository = giftcardRepository;
        this.giftcardService = giftcardService;
    }

    public List<TransactionDTO> findAll() {
        final List<Transaction> transactions = transactionRepository.findAll(Sort.by("id"));
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .toList();
    }

    public TransactionDTO get(final UUID id) {
        return transactionRepository.findById(id)
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public GiftcardTransactionResponseDTO createGiftcardTransaction(final GiftcardTransactionDTO giftcardTransactionDTO){
        final GiftcardTransactionResponseDTO giftcardTransactionResponseDTO = new GiftcardTransactionResponseDTO();
        final GiftcardDTO giftcardDTO = new GiftcardDTO();

        //TODO:check if gift card is available,
        // if not create giftcard
        List<UUID> issuerList = new ArrayList<>();
        issuerList.add(giftcardTransactionDTO.getIssuerId());

        giftcardDTO.setCode(generateGiftCardCode());
        giftcardDTO.setCvv(generateGiftCardCvv());
        giftcardDTO.setValue(giftcardTransactionDTO.getGiftCardValue());
        giftcardDTO.setExpirationDate(LocalDate.now());
        giftcardDTO.setMessage(giftcardTransactionDTO.getGiftCardMessage());
        giftcardDTO.setRecipientPhoneNumber(giftcardTransactionDTO.getRecipient());
        giftcardDTO.setReceipientName("");
        giftcardDTO.setStatus(GiftcardStatus.PENDING);
        giftcardDTO.setTitle(giftcardTransactionDTO.getGiftcartTitle());
        giftcardDTO.setIssuers(issuerList);
        giftcardDTO.setPurchaser(giftcardTransactionDTO.getPurchaserId());
        giftcardDTO.setRecipient();
        giftcardDTO.setPurchaserName();
        giftcardDTO.setTheme(UUID.fromString("33f7d67c-78dd-4569-ba7c-21c38639c5d7"));

        UUID giftCardId = giftcardService.create(giftcardDTO);


        //do transaction

        giftcardTransactionResponseDTO.setCode(200);
        giftcardTransactionResponseDTO.setMessage("transaction success");
        giftcardTransactionResponseDTO.setStatus(true);

        return giftcardTransactionResponseDTO;
    }

    public UUID create(final TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);
        return transactionRepository.save(transaction).getId();
    }

    public void update(final UUID id, final TransactionDTO transactionDTO) {
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionDTO, transaction);
        transactionRepository.save(transaction);
    }

    public void delete(final UUID id) {
        transactionRepository.deleteById(id);
    }

    private TransactionDTO mapToDTO(final Transaction transaction,
            final TransactionDTO transactionDTO) {
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setExternalTransactionRef(transaction.getExternalTransactionRef());
        transactionDTO.setProvider(transaction.getProvider());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setEmail(transaction.getEmail());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setNarrative(transaction.getNarrative());
        transactionDTO.setGiftcard(transaction.getGiftcard() == null ? null : transaction.getGiftcard().getId());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setExternalTransactionRef(transactionDTO.getExternalTransactionRef());
        transaction.setProvider(transactionDTO.getProvider());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setEmail(transactionDTO.getEmail());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setNarrative(transactionDTO.getNarrative());
        final Giftcard giftcard = transactionDTO.getGiftcard() == null ? null : giftcardRepository.findById(transactionDTO.getGiftcard())
                .orElseThrow(() -> new NotFoundException("giftcard not found"));
        transaction.setGiftcard(giftcard);
        return transaction;
    }

    private String generateGiftCardCode(){
        SecureRandom secureRandom = new SecureRandom();
        // Generate a random number of 12 digits
        long randomNumber = 100000000000L + secureRandom.nextLong() % 900000000000L;
        return String.valueOf(randomNumber);
    }

    private int generateGiftCardCvv(){
        SecureRandom secureRandom = new SecureRandom();

        // Generate a random number of 12 digits
        long randomNumber = 100L + secureRandom.nextLong() % 900L;

        return (int) randomNumber;
    }
}
