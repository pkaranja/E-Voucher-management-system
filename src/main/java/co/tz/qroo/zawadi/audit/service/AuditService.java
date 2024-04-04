package co.tz.qroo.zawadi.audit.service;

import co.tz.qroo.zawadi.audit.domain.Audit;
import co.tz.qroo.zawadi.audit.model.AuditDTO;
import co.tz.qroo.zawadi.audit.repos.AuditRepository;
import co.tz.qroo.zawadi.user.domain.User;
import co.tz.qroo.zawadi.user.repos.UserRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;

    public AuditService(final AuditRepository auditRepository,
            final UserRepository userRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
    }

    public List<AuditDTO> findAll() {
        final List<Audit> audits = auditRepository.findAll(Sort.by("id"));
        return audits.stream()
                .map(audit -> mapToDTO(audit, new AuditDTO()))
                .toList();
    }

    public AuditDTO get(final UUID id) {
        return auditRepository.findById(id)
                .map(audit -> mapToDTO(audit, new AuditDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final AuditDTO auditDTO) {
        final Audit audit = new Audit();
        mapToEntity(auditDTO, audit);
        return auditRepository.save(audit).getId();
    }

    public void update(final UUID id, final AuditDTO auditDTO) {
        final Audit audit = auditRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(auditDTO, audit);
        auditRepository.save(audit);
    }

    public void delete(final UUID id) {
        auditRepository.deleteById(id);
    }

    private AuditDTO mapToDTO(final Audit audit, final AuditDTO auditDTO) {
        auditDTO.setId(audit.getId());
        auditDTO.setAction(audit.getAction());
        auditDTO.setDetails(audit.getDetails());
        auditDTO.setUser(audit.getUser() == null ? null : audit.getUser().getId());
        return auditDTO;
    }

    private Audit mapToEntity(final AuditDTO auditDTO, final Audit audit) {
        audit.setAction(auditDTO.getAction());
        audit.setDetails(auditDTO.getDetails());
        final User user = auditDTO.getUser() == null ? null : userRepository.findById(auditDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        audit.setUser(user);
        return audit;
    }

    public boolean userExists(final UUID id) {
        return auditRepository.existsByUserId(id);
    }

}
