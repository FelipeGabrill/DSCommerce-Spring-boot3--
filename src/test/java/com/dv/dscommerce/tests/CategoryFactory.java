package com.dv.dscommerce.tests;

import com.dv.dscommerce.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(1L, "Games");
	}
	
	public static Category createCategory(Long id, String name) {
		return new Category(id, name);
	}
}
