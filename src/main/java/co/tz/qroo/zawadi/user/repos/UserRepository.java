package co.tz.qroo.zawadi.user.repos;

import co.tz.qroo.zawadi.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByExternalId(UUID externalId);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

}
