package com.dv.dscommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dv.dscommerce.dto.ProductDTO;
import com.dv.dscommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping("/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping
	public Page<ProductDTO> findByAll(Pageable pageable) {
		return service.findAll(pageable);
	}
}
