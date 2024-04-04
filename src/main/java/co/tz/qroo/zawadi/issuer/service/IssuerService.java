package co.tz.qroo.zawadi.issuer.service;

import co.tz.qroo.zawadi.category.domain.Category;
import co.tz.qroo.zawadi.category.repos.CategoryRepository;
import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.issuer.domain.Issuer;
import co.tz.qroo.zawadi.issuer.model.IssuerDTO;
import co.tz.qroo.zawadi.issuer.repos.IssuerRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import co.tz.qroo.zawadi.util.RestApiFilter.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class IssuerService {
    private static final Logger logger = LoggerFactory.getLogger(IssuerService.class);
    private final IssuerRepository issuerRepository;
    private final CategoryRepository categoryRepository;
    private final GiftcardRepository giftcardRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public IssuerService(final IssuerRepository issuerRepository,
            final CategoryRepository categoryRepository,
            final GiftcardRepository giftcardRepository) {
        this.issuerRepository = issuerRepository;
        this.categoryRepository = categoryRepository;
        this.giftcardRepository = giftcardRepository;
    }

    public List<IssuerDTO> findAll() {
        final List<Issuer> issuers = issuerRepository.findAll(Sort.by("id"));
        return issuers.stream()
                .map(issuer -> mapToDTO(issuer, new IssuerDTO()))
                .toList();
    }

    public Page<IssuerDTO> searchAll(SearchRequest request) {
        SearchSpecification<Issuer> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<Issuer> issuerPage = issuerRepository.findAll(specification, pageable);
        return issuerPage.map(issuer -> mapToDTO(issuer, new IssuerDTO()));
    }

    public Page<IssuerDTO> fetchIssuersByCategory(Long categoryId, SearchRequest request) {
        //Get Issuer ID's from issuer category
        List<UUID> issuerIds = issuerRepository.findAllIssuerIdsByCategory(categoryId);

        // Convert List<UUID> to List<Object>
        List<Object> objectList = issuerIds.stream()
                .map(uuid -> (Object) uuid)
                .collect(Collectors.toList());

        FilterRequest categoryFilterRequest = new FilterRequest();
        categoryFilterRequest.setKey("id");
        categoryFilterRequest.setOperator(Operator.IN);
        categoryFilterRequest.setFieldType(FieldType.STRING);
        categoryFilterRequest.setValues(objectList);
        request.getFilters().add(categoryFilterRequest);
        // End Categories Filter


        SearchSpecification<Issuer> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<Issuer> issuerPage = issuerRepository.findAll(specification, pageable);
        return issuerPage.map(issuer -> mapToDTO(issuer, new IssuerDTO()));
    }

    public IssuerDTO get(final UUID id) {
        return issuerRepository.findById(id)
                .map(issuer -> mapToDTO(issuer, new IssuerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final IssuerDTO issuerDTO) {
        final Issuer issuer = new Issuer();
        mapToEntity(issuerDTO, issuer);
        return issuerRepository.save(issuer).getId();
    }

    public void update(final UUID id, final IssuerDTO issuerDTO) {
        final Issuer issuer = issuerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(issuerDTO, issuer);
        issuerRepository.save(issuer);
    }

    public void delete(final UUID id) {
        issuerRepository.deleteById(id);
    }

    private IssuerDTO mapToDTO(final Issuer issuer, final IssuerDTO issuerDTO) {
        issuerDTO.setId(issuer.getId());
        issuerDTO.setName(issuer.getName());
        issuerDTO.setEmail(issuer.getEmail());
        issuerDTO.setPhone(issuer.getPhone());
        issuerDTO.setWebsite(issuer.getWebsite());
        issuerDTO.setAddress(issuer.getAddress());
        issuerDTO.setHits(issuer.getHits());
        issuerDTO.setLogo(issuer.getLogo());
        issuerDTO.setFacebook(issuer.getFacebook());
        issuerDTO.setInstagram(issuer.getInstagram());
        issuerDTO.setPrimaryColor(issuer.getPrimaryColor());
        issuerDTO.setSecondaryColor(issuer.getSecondaryColor());
        issuerDTO.setPrimaryFontColor(issuer.getPrimaryFontColor());
        issuerDTO.setSecondaryFontColor(issuer.getSecondaryFontColor());
        issuerDTO.setDescription(issuer.getDescription());
        issuerDTO.setMinAmount(issuer.getMinAmount());
        issuerDTO.setMaxAmount(issuer.getMaxAmount());
        issuerDTO.setPopular(issuer.getPopular());
        issuerDTO.setFeatured(issuer.getFeatured());
        issuerDTO.setStatus(issuer.getStatus());
        issuerDTO.setCategory(issuer.getCategory().stream()
                .map(category -> category.getId())
                .toList());
        return issuerDTO;
    }

    private Issuer mapToEntity(final IssuerDTO issuerDTO, final Issuer issuer) {
        issuer.setName(issuerDTO.getName());
        issuer.setEmail(issuerDTO.getEmail());
        issuer.setPhone(issuerDTO.getPhone());
        issuer.setWebsite(issuerDTO.getWebsite());
        issuer.setAddress(issuerDTO.getAddress());
        issuer.setHits(issuerDTO.getHits());
        issuer.setLogo(issuerDTO.getLogo());
        issuer.setFacebook(issuerDTO.getFacebook());
        issuer.setInstagram(issuerDTO.getInstagram());
        issuer.setPrimaryColor(issuerDTO.getPrimaryColor());
        issuer.setSecondaryColor(issuerDTO.getSecondaryColor());
        issuer.setPrimaryFontColor(issuerDTO.getPrimaryFontColor());
        issuer.setSecondaryFontColor(issuerDTO.getSecondaryFontColor());
        issuer.setDescription(issuerDTO.getDescription());
        issuer.setMinAmount(issuerDTO.getMinAmount());
        issuer.setMaxAmount(issuerDTO.getMaxAmount());
        issuer.setPopular(issuerDTO.getPopular());
        issuer.setFeatured(issuerDTO.getFeatured());
        issuer.setStatus(issuerDTO.getStatus());
        final List<Category> category = categoryRepository.findAllById(
                issuerDTO.getCategory() == null ? Collections.emptyList() : issuerDTO.getCategory());
        if (category.size() != (issuerDTO.getCategory() == null ? 0 : issuerDTO.getCategory().size())) {
            throw new NotFoundException("one of category not found");
        }
        issuer.setCategory(new HashSet<>(category));
        return issuer;
    }

    public boolean nameExists(final String name) {
        return issuerRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Issuer issuer = issuerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Giftcard issuerGiftcard = giftcardRepository.findFirstByIssuer(issuer);
        if (issuerGiftcard != null) {
            referencedWarning.setKey("issuer.giftcard.issuer.referenced");
            referencedWarning.addParam(issuerGiftcard.getId());
            return referencedWarning;
        }
        return null;
    }

}
