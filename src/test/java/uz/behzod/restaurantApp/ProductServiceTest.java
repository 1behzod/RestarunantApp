package uz.behzod.restaurantApp;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.dto.product.ProductDTO;
import uz.behzod.restaurantApp.repository.ProductRepository;
import uz.behzod.restaurantApp.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void testCreate() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDepartmentId(1L);
        productDTO.setBarcode("12345");
        productDTO.setUnitId(1L);

        Product product = new Product();
        product.setId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Long productId = productService.create(productDTO);

        assertNotNull(productId);
        assertEquals(1L, productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

}
