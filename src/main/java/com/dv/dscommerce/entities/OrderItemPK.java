package com.dv.dscommerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemPK {

	@ManyToOne
	@JoinColumn(name = "order_id")
	@EqualsAndHashCode.Include
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@EqualsAndHashCode.Include
	private Product product;
	
	public OrderItemPK() {
	}
}
