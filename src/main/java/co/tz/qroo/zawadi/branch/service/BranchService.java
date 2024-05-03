package co.tz.qroo.zawadi.branch.service;

import co.tz.qroo.zawadi.branch.domain.Branch;
import co.tz.qroo.zawadi.branch.model.BranchDTO;
import co.tz.qroo.zawadi.branch.repos.BranchRepository;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.issuer.repos.IssuerRepository;
import co.tz.qroo.zawadi.payment_method.domain.PaymentMethod;
import co.tz.qroo.zawadi.payment_method.repos.PaymentMethodRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final IssuerRepository issuerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public BranchService(final BranchRepository branchRepository,
            final IssuerRepository issuerRepository,
            final PaymentMethodRepository paymentMethodRepository) {
        this.branchRepository = branchRepository;
        this.issuerRepository = issuerRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<BranchDTO> findAll() {
        final List<Branch> branches = branchRepository.findAll(Sort.by("id"));
        return branches.stream()
                .map(branch -> mapToDTO(branch, new BranchDTO()))
                .toList();
    }

    public BranchDTO get(final UUID id) {
        return branchRepository.findById(id)
                .map(branch -> mapToDTO(branch, new BranchDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final BranchDTO branchDTO) {
        final Branch branch = new Branch();
        mapToEntity(branchDTO, branch);
        return branchRepository.save(branch).getId();
    }

    public void update(final UUID id, final BranchDTO branchDTO) {
        final Branch branch = branchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(branchDTO, branch);
        branchRepository.save(branch);
    }

    public void delete(final UUID id) {
        branchRepository.deleteById(id);
    }

    private BranchDTO mapToDTO(final Branch branch, final BranchDTO branchDTO) {
        branchDTO.setId(branch.getId());
        branchDTO.setName(branch.getName());
        branchDTO.setAddress(branch.getAddress());
        branchDTO.setPhoneNumber(branch.getPhoneNumber());
        branchDTO.setEmailAddress(branch.getEmailAddress());
        branchDTO.setContactPersonName(branch.getContactPersonName());
        branchDTO.setIssuer(branch.getIssuer() == null ? null : branch.getIssuer().getId());
        return branchDTO;
    }

    private Branch mapToEntity(final BranchDTO branchDTO, final Branch branch) {
        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setPhoneNumber(branchDTO.getPhoneNumber());
        branch.setEmailAddress(branchDTO.getEmailAddress());
        branch.setContactPersonName(branchDTO.getContactPersonName());
        final Issuer issuer = branchDTO.getIssuer() == null ? null : issuerRepository.findById(branchDTO.getIssuer())
                .orElseThrow(() -> new NotFoundException("issuer not found"));
        branch.setIssuer(issuer);
        return branch;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Branch branch = branchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final PaymentMethod branchPaymentMethod = paymentMethodRepository.findFirstByBranch(branch);
        if (branchPaymentMethod != null) {
            referencedWarning.setKey("branch.paymentMethod.branch.referenced");
            referencedWarning.addParam(branchPaymentMethod.getId());
            return referencedWarning;
        }
        return null;
    }

}
