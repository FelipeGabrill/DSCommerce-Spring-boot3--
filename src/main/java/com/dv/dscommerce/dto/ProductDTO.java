package com.dv.dscommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.dv.dscommerce.entities.Category;
import com.dv.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDTO {

	private Long id;
	
	@Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@Size(min = 10, message = "Descrição precisa ter no minimo 10 caracteres")
	@NotBlank(message = "Campo requerido")
	private String description;
	
	@NotNull(message = "Campo requerido")
	@Positive(message = "O preço deve ser positivo")
	private Double price;
	
	private String imgUrl;
	
	@NotEmpty(message = "Deve ter pelo menos uma categoria")
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		for (Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat));
		}
	}
}
