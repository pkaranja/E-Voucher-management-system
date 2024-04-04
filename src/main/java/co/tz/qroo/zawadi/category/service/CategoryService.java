package co.tz.qroo.zawadi.category.service;

import co.tz.qroo.zawadi.category.domain.Category;
import co.tz.qroo.zawadi.category.model.CategoryDTO;
import co.tz.qroo.zawadi.category.repos.CategoryRepository;
import co.tz.qroo.zawadi.issuer.repos.IssuerRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.RestApiFilter.SearchRequest;
import co.tz.qroo.zawadi.util.RestApiFilter.SearchSpecification;
import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final IssuerRepository issuerRepository;

    public CategoryService(final CategoryRepository categoryRepository,
                           final IssuerRepository issuerRepository) {
        this.categoryRepository = categoryRepository;
        this.issuerRepository = issuerRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public Page<CategoryDTO> searchAll(SearchRequest request) {
        SearchSpecification<Category> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<Category> categoryPage = categoryRepository.findAll(specification, pageable);
        return categoryPage.map(category -> mapToDTO(category, new CategoryDTO()));
    }

    public CategoryDTO get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        issuerRepository.findAllByCategory(category)
                .forEach(issuer -> issuer.getCategory().remove(category));
        categoryRepository.delete(category);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        categoryDTO.setIcon(category.getIcon());
        categoryDTO.setBackgroundColor(category.getBackgroundColor());
        categoryDTO.setOrder(category.getOrder());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        category.setIcon(categoryDTO.getIcon());
        category.setBackgroundColor(categoryDTO.getBackgroundColor());
        category.setOrder(categoryDTO.getOrder());
        return category;
    }

    public boolean nameExists(final String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

}
