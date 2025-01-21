package com.dv.dscommerce.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dv.dscommerce.dto.OrderDTO;
import com.dv.dscommerce.entities.Order;
import com.dv.dscommerce.entities.OrderItem;
import com.dv.dscommerce.entities.Product;
import com.dv.dscommerce.entities.User;
import com.dv.dscommerce.repositories.OrderItemRepository;
import com.dv.dscommerce.repositories.OrderRepository;
import com.dv.dscommerce.repositories.ProductRepository;
import com.dv.dscommerce.services.exceptions.ForbiddenException;
import com.dv.dscommerce.services.exceptions.ResourceNotFoundException;
import com.dv.dscommerce.tests.OrderFactory;
import com.dv.dscommerce.tests.ProductFactory;
import com.dv.dscommerce.tests.UserFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;

	@Mock
	private OrderRepository repository;

	@Mock
	private AuthService authService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private OrderItemRepository orderItemRepository;

	@Mock
	private UserService userService;

	private Long existingOrderId, nonExistingOrderId;
	private Long existingProductId, nonExistingProductId;

	private Product product;

	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;

	@BeforeEach
	void setUp() throws Exception {
		existingOrderId = 1L;
		nonExistingOrderId = 1000L;

		existingProductId = 1L;
		nonExistingProductId = 1000L;

		admin = UserFactory.createCustomAdminUser(1L, "bob");
		client = UserFactory.createCustomClientUser(2L, "ana");

		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);

		product = ProductFactory.createProduct();

		Mockito.when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
		Mockito.when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

		Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.save(any())).thenReturn(order);
		Mockito.when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>(order.getItems()));
	}

	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

		OrderDTO result = service.findById(existingOrderId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingOrderId);

	}

	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogged() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

		OrderDTO result = service.findById(existingOrderId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingOrderId);
	}

	@Test
	public void findByIdShouldThrowsForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
		Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());

		Assertions.assertThrows(ForbiddenException.class, () -> {
			OrderDTO result = service.findById(existingOrderId);
		});
	}

	@Test
	public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists() {
		Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			OrderDTO result = service.findById(nonExistingOrderId);
		});
	}

	@Test
	public void insertShouldReturnOrderDTOWhenAdminLogged() {

		Mockito.when(userService.authenticated()).thenReturn(admin);

		OrderDTO result = service.insert(orderDTO);

		Assertions.assertNotNull(result);
	}

	@Test
	public void insertShouldReturnOrderDTOWhenClientLogged() {

		Mockito.when(userService.authenticated()).thenReturn(client);

		OrderDTO result = service.insert(orderDTO);

		Assertions.assertNotNull(result);
	}

	@Test
	public void insertShouldThrowsUsernameNotFoundExceptionWhenUserNotLogged() {

		Mockito.doThrow(UsernameNotFoundException.class).when(userService).authenticated();

		order.setClient(new User());
		orderDTO = new OrderDTO(order);
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
	
	@Test
	public void insertThrowsEntityNotFoundExceptionWhenOrderProductIdDoesNotExist() {
		
		Mockito.when(userService.authenticated()).thenReturn(client);

		product.setId(nonExistingProductId);
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		
		orderDTO = new OrderDTO(order);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
}
