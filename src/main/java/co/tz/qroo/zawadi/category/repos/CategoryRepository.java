package co.tz.qroo.zawadi.category.repos;

import co.tz.qroo.zawadi.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {
    boolean existsByNameIgnoreCase(String name);

}
