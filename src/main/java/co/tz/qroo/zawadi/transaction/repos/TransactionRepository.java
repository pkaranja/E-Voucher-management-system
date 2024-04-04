package co.tz.qroo.zawadi.transaction.repos;

import co.tz.qroo.zawadi.transaction.domain.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
