package com.dv.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends CustomError {

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Instant timestamp, Integer status, String erro, String path) {
		super(timestamp, status, erro, path);
	}
	
	public void addError(String fieldName, String message) {
		errors.removeIf(x -> x.getFielName().equals(fieldName));	
		errors.add(new FieldMessage(fieldName, message));
	}
}
