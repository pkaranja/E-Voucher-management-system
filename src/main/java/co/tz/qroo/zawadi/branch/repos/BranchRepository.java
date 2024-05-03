package co.tz.qroo.zawadi.branch.repos;

import co.tz.qroo.zawadi.branch.domain.Branch;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BranchRepository extends JpaRepository<Branch, UUID> {

    Branch findFirstByIssuer(Issuer issuer);

}
