package com.dv.dscommerce.tests;

import com.dv.dscommerce.entities.Category;
import com.dv.dscommerce.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
		Category category = CategoryFactory.createCategory();
		Product product = new Product(1L, "PS5", "Lorem ipsum dolor sit amet", 2999.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");
		product.getCategories().add(category);
		return product;
	}
	
	public static Product createProduct(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}
}
