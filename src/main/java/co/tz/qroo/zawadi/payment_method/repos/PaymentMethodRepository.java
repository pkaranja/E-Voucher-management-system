package co.tz.qroo.zawadi.payment_method.repos;

import co.tz.qroo.zawadi.branch.domain.Branch;
import co.tz.qroo.zawadi.payment_method.domain.PaymentMethod;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    PaymentMethod findFirstByBranch(Branch branch);

}
