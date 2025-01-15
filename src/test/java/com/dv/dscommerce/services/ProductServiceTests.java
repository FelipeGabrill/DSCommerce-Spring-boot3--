package com.dv.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dv.dscommerce.dto.ProductDTO;
import com.dv.dscommerce.dto.ProductMinDTO;
import com.dv.dscommerce.entities.Product;
import com.dv.dscommerce.repositories.ProductRepository;
import com.dv.dscommerce.services.exceptions.ResourceNotFoundException;
import com.dv.dscommerce.tests.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingProductId, nonExistingProductId;
	private String productName;
	
	private Product product;
	
	private PageImpl<Product> page;
	
	@BeforeEach
	void setUp() throws Exception {
		existingProductId = 1L;
		nonExistingProductId = 1000L;
		productName = "Console PS5";
		
		product = ProductFactory.createProduct(productName);
		page = new PageImpl<>(List.of(product)); 
		
		Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		Mockito.when(repository.searchByName(any(), (Pageable)any())).thenReturn(page);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingProductId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingProductId);
		Assertions.assertEquals(result.getName(), productName);
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}
	
	@Test
	public void findAllShouldReturnPagedProductMinDTO() {
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<ProductMinDTO> result = service.findAll(productName, pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1L);
		Assertions.assertEquals(result.iterator().next().getName(), productName);

	}
}
