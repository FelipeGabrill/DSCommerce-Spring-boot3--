package com.dv.dscommerce.tests;

import java.time.Instant;

import com.dv.dscommerce.entities.Order;
import com.dv.dscommerce.entities.OrderItem;
import com.dv.dscommerce.entities.OrderStatus;
import com.dv.dscommerce.entities.Payment;
import com.dv.dscommerce.entities.Product;
import com.dv.dscommerce.entities.User;

public class OrderFactory {

	public static Order createOrder(User user) {
		Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, user, new Payment());
		
		Product product = ProductFactory.createProduct();
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		
		return order;
	}
	
}
