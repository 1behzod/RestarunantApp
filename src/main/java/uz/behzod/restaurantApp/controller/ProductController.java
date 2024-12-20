package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.product.ProductDTO;
import uz.behzod.restaurantApp.dto.product.ProductDetailDTO;
import uz.behzod.restaurantApp.dto.product.ProductListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.ProductService;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {

    ProductService productService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductListDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(productService.getList(filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
