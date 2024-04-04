package co.tz.qroo.zawadi.transaction.service;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.transaction.model.TransactionDTO;
import co.tz.qroo.zawadi.transaction.repos.TransactionRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
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
        transactionDTO.setPaymentMethod(transaction.getPaymentMethod());
        transactionDTO.setExternalId(transaction.getExternalId());
        transactionDTO.setProvider(transaction.getProvider());
        transactionDTO.setStatus(transaction.getStatus());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setExternalId(transactionDTO.getExternalId());
        transaction.setProvider(transactionDTO.getProvider());
        transaction.setStatus(transactionDTO.getStatus());
        return transaction;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Giftcard transactionGiftcard = giftcardRepository.findFirstByTransaction(transaction);
        if (transactionGiftcard != null) {
            referencedWarning.setKey("transaction.giftcard.transaction.referenced");
            referencedWarning.addParam(transactionGiftcard.getId());
            return referencedWarning;
        }
        return null;
    }

}
