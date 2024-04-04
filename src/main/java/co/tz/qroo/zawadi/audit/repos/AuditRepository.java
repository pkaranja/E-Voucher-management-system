package co.tz.qroo.zawadi.audit.repos;

import co.tz.qroo.zawadi.audit.domain.Audit;
import co.tz.qroo.zawadi.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditRepository extends JpaRepository<Audit, UUID> {

    Audit findFirstByUser(User user);

    boolean existsByUserId(UUID id);

}
