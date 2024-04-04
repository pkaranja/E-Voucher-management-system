package co.tz.qroo.zawadi.category.rest;

import co.tz.qroo.zawadi.category.model.CategoryDTO;
import co.tz.qroo.zawadi.category.service.CategoryService;
import co.tz.qroo.zawadi.issuer.model.IssuerDTO;
import co.tz.qroo.zawadi.issuer.service.IssuerService;
import co.tz.qroo.zawadi.util.RestApiFilter.SearchRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryResource {

    private final CategoryService categoryService;
    @Autowired
    IssuerService issuerService;

    public CategoryResource(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping
    public Page<CategoryDTO> searchCategories(@RequestBody SearchRequest request) { return categoryService.searchAll(request); }

    @PostMapping("/{id}/issuers")
    public Page<IssuerDTO> searchIssuers(@RequestBody SearchRequest request, @PathVariable(name = "id") final Long id ){
        return issuerService.fetchIssuersByCategory(id , request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCategory(@RequestBody @Valid final CategoryDTO categoryDTO) {
        final Long createdId = categoryService.create(categoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCategory(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") final Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
