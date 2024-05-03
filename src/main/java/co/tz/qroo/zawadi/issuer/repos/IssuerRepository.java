package co.tz.qroo.zawadi.issuer.repos;

import co.tz.qroo.zawadi.category.domain.Category;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface IssuerRepository extends JpaRepository<Issuer, UUID>, JpaSpecificationExecutor<Issuer> {

    boolean existsByNameIgnoreCase(String name);
    Issuer findFirstByCategory(Category category);
    List<Issuer> findAllByCategory(Category category);

    @Query(value = "SELECT ic.issuer_id FROM issuer_categories ic WHERE ic.category_id = :categoryId", nativeQuery = true)
    List<UUID> findAllIssuerIdsByCategory(UUID categoryId);

}
