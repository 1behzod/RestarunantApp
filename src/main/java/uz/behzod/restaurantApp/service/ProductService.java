package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.product.ProductDTO;
import uz.behzod.restaurantApp.dto.product.ProductDetailDTO;
import uz.behzod.restaurantApp.dto.product.ProductListDTO;
import uz.behzod.restaurantApp.filters.product.ProductFilter;
import uz.behzod.restaurantApp.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    ProductRepository productRepository;

    public void validate(ProductDTO productDTO) {
        if (!StringUtils.hasLength(productDTO.getName())) {
            throw new RuntimeException("Product name is required");
        }
        if (productDTO.getDepartmentId() == null) {
            throw new RuntimeException("Department id is required");
        }
        if (productDTO.getBarcode() == null) {
            throw new RuntimeException("Barcode is required");
        }
       /* if (productDTO.getUnitId() == null) {
            throw new RuntimeException("Unit id is required");
        }*/
    }


    @Transactional
    public Long create(ProductDTO productDTO) {
        this.validate(productDTO);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDepartmentId(productDTO.getDepartmentId());
        product.setBarcode(productDTO.getBarcode());
        product.setUnitId(productDTO.getUnitId());
        if (StringUtils.hasLength(productDTO.getDescription())) {
            product.setDescription(productDTO.getDescription());
        }
        return productRepository.save(product).getId();
    }

    @Transactional
    public Long update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setId(id);
        this.validate(productDTO);

        product.setName(productDTO.getName());
        product.setDepartmentId(productDTO.getDepartmentId());
        product.setBarcode(productDTO.getBarcode());
        product.setUnitId(productDTO.getUnitId());
        if (StringUtils.hasLength(productDTO.getDescription())) {
            product.setDescription(productDTO.getDescription());
        }
        return productRepository.save(product).getId();
    }

    @Transactional
    public void delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        throw new RuntimeException("Product not found");
    }

    public ProductDetailDTO get(Long id) {
        return productRepository.findById(id).map(product -> {
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();

            productDetailDTO.setId(product.getId());
            productDetailDTO.setName(product.getName());
            productDetailDTO.setBarcode(product.getBarcode());
            productDetailDTO.setUnitId(product.getUnitId());
            productDetailDTO.setDepartment(product.getDepartment().toCommonDTO());
            if (StringUtils.hasLength(product.getDescription())) {
                productDetailDTO.setDescription(product.getDescription());
            }
            return productDetailDTO;

        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public Page<ProductListDTO> getList(ProductFilter filter) {
        ResultList<Product> resultList = productRepository.getResultList(filter);
        List<ProductListDTO> result = resultList
                .getList()
                .stream()
                .map(product -> {
                    ProductListDTO productListDTO = new ProductListDTO();
                    productListDTO.setId(product.getId());
                    productListDTO.setName(product.getName());
                    productListDTO.setBarcode(product.getBarcode());
                    productListDTO.setDepartment(product.getDepartment().toCommonDTO());
                    return productListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }

}
