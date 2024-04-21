package com.dv.dscommerce.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_order_item")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private OrderItemPK id = new OrderItemPK();
	
	@Getter
	@Setter
	private Integer quantity;
	
	@Getter
	@Setter
	private Double price;
	
	public OrderItem() {
	}

	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}
	
	public Order getOrder() {
		return id.getOrder();
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
}
