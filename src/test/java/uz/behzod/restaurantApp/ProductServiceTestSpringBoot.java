package uz.behzod.restaurantApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.dto.product.ProductDTO;
import uz.behzod.restaurantApp.repository.ProductRepository;
import uz.behzod.restaurantApp.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class ProductServiceTestSpringBoot {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testCreate() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDepartmentId(1L);
        productDTO.setBarcode("12345");

        Product product = new Product();
        product.setId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Long productId = productService.create(productDTO);

        assertNotNull(productId);
        assertEquals(1L, productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }


}
