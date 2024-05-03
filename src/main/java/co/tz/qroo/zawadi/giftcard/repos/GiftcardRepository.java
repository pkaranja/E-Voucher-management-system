package co.tz.qroo.zawadi.giftcard.repos;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.theme.domain.Theme;
import co.tz.qroo.zawadi.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GiftcardRepository extends JpaRepository<Giftcard, UUID> {
    Giftcard findFirstByIssuers(Issuer issuer);

    Giftcard findFirstByPurchaser(User user);

    Giftcard findFirstByRecipient(User user);

    Giftcard findFirstByTheme(Theme theme);

    List<Giftcard> findAllByIssuers(Issuer issuer);

    boolean existsByCodeIgnoreCase(String code);
}
