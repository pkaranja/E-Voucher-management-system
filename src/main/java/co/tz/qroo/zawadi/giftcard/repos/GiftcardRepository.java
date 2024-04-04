package co.tz.qroo.zawadi.giftcard.repos;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.theme.domain.Theme;
import co.tz.qroo.zawadi.transaction.domain.Transaction;
import co.tz.qroo.zawadi.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GiftcardRepository extends JpaRepository<Giftcard, UUID> {

    Giftcard findFirstByIssuer(Issuer issuer);

    Giftcard findFirstByPurchaser(User user);

    Giftcard findFirstByRecipient(User user);

    Giftcard findFirstByTheme(Theme theme);

    Giftcard findFirstByTransaction(Transaction transaction);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByThemeId(UUID id);

    boolean existsByTransactionId(UUID id);

}
