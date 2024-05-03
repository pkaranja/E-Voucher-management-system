package co.tz.qroo.zawadi.transaction.service;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.transaction.model.TransactionDTO;
import co.tz.qroo.zawadi.transaction.repos.TransactionRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final GiftcardRepository giftcardRepository;

    public TransactionService(final TransactionRepository transactionRepository,
            final GiftcardRepository giftcardRepository) {
        this.transactionRepository = transactionRepository;
        this.giftcardRepository = giftcardRepository;
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

}
