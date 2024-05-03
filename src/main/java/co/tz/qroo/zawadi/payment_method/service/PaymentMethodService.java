package co.tz.qroo.zawadi.payment_method.service;

import co.tz.qroo.zawadi.branch.domain.Branch;
import co.tz.qroo.zawadi.branch.repos.BranchRepository;
import co.tz.qroo.zawadi.payment_method.domain.PaymentMethod;
import co.tz.qroo.zawadi.payment_method.model.PaymentMethodDTO;
import co.tz.qroo.zawadi.payment_method.repos.PaymentMethodRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final BranchRepository branchRepository;

    public PaymentMethodService(final PaymentMethodRepository paymentMethodRepository,
            final BranchRepository branchRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.branchRepository = branchRepository;
    }

    public List<PaymentMethodDTO> findAll() {
        final List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll(Sort.by("id"));
        return paymentMethods.stream()
                .map(paymentMethod -> mapToDTO(paymentMethod, new PaymentMethodDTO()))
                .toList();
    }

    public PaymentMethodDTO get(final UUID id) {
        return paymentMethodRepository.findById(id)
                .map(paymentMethod -> mapToDTO(paymentMethod, new PaymentMethodDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PaymentMethodDTO paymentMethodDTO) {
        final PaymentMethod paymentMethod = new PaymentMethod();
        mapToEntity(paymentMethodDTO, paymentMethod);
        return paymentMethodRepository.save(paymentMethod).getId();
    }

    public void update(final UUID id, final PaymentMethodDTO paymentMethodDTO) {
        final PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentMethodDTO, paymentMethod);
        paymentMethodRepository.save(paymentMethod);
    }

    public void delete(final UUID id) {
        paymentMethodRepository.deleteById(id);
    }

    private PaymentMethodDTO mapToDTO(final PaymentMethod paymentMethod,
            final PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setId(paymentMethod.getId());
        paymentMethodDTO.setProvider(paymentMethod.getProvider());
        paymentMethodDTO.setPaymentNumber(paymentMethod.getPaymentNumber());
        paymentMethodDTO.setStatus(paymentMethod.getStatus());
        paymentMethodDTO.setBranch(paymentMethod.getBranch() == null ? null : paymentMethod.getBranch().getId());
        return paymentMethodDTO;
    }

    private PaymentMethod mapToEntity(final PaymentMethodDTO paymentMethodDTO,
            final PaymentMethod paymentMethod) {
        paymentMethod.setProvider(paymentMethodDTO.getProvider());
        paymentMethod.setPaymentNumber(paymentMethodDTO.getPaymentNumber());
        paymentMethod.setStatus(paymentMethodDTO.getStatus());
        final Branch branch = paymentMethodDTO.getBranch() == null ? null : branchRepository.findById(paymentMethodDTO.getBranch())
                .orElseThrow(() -> new NotFoundException("branch not found"));
        paymentMethod.setBranch(branch);
        return paymentMethod;
    }

}
