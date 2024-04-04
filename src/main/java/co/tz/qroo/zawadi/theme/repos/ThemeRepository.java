package co.tz.qroo.zawadi.theme.repos;

import co.tz.qroo.zawadi.theme.domain.Theme;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ThemeRepository extends JpaRepository<Theme, UUID> {
}
