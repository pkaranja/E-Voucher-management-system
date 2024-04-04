package co.tz.qroo.zawadi.user.service;

import co.tz.qroo.zawadi.audit.domain.Audit;
import co.tz.qroo.zawadi.audit.repos.AuditRepository;
import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.user.domain.User;
import co.tz.qroo.zawadi.user.model.UserDTO;
import co.tz.qroo.zawadi.user.repos.UserRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final GiftcardRepository giftcardRepository;
    private final AuditRepository auditRepository;

    public UserService(final UserRepository userRepository,
            final GiftcardRepository giftcardRepository, final AuditRepository auditRepository) {
        this.userRepository = userRepository;
        this.giftcardRepository = giftcardRepository;
        this.auditRepository = auditRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final UUID id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final UUID id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setExternalId(user.getExternalId());
        userDTO.setAddress(user.getAddress());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setGiftcardsPurchased(user.getGiftcardsPurchased());
        userDTO.setGiftcardsReceived(user.getGiftcardsReceived());
        userDTO.setStatus(user.getStatus());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setExternalId(userDTO.getExternalId());
        user.setAddress(userDTO.getAddress());
        user.setLastLogin(userDTO.getLastLogin());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGiftcardsPurchased(userDTO.getGiftcardsPurchased());
        user.setGiftcardsReceived(userDTO.getGiftcardsReceived());
        user.setStatus(userDTO.getStatus());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        return user;
    }

    public boolean externalIdExists(final UUID externalId) {
        return userRepository.existsByExternalId(externalId);
    }

    public boolean phoneNumberExists(final String phoneNumber) {
        return userRepository.existsByPhoneNumberIgnoreCase(phoneNumber);
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Giftcard purchaserGiftcard = giftcardRepository.findFirstByPurchaser(user);
        if (purchaserGiftcard != null) {
            referencedWarning.setKey("user.giftcard.purchaser.referenced");
            referencedWarning.addParam(purchaserGiftcard.getId());
            return referencedWarning;
        }
        final Giftcard recipientGiftcard = giftcardRepository.findFirstByRecipient(user);
        if (recipientGiftcard != null) {
            referencedWarning.setKey("user.giftcard.recipient.referenced");
            referencedWarning.addParam(recipientGiftcard.getId());
            return referencedWarning;
        }
        final Audit userAudit = auditRepository.findFirstByUser(user);
        if (userAudit != null) {
            referencedWarning.setKey("user.audit.user.referenced");
            referencedWarning.addParam(userAudit.getId());
            return referencedWarning;
        }
        return null;
    }

}