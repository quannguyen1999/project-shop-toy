package com.springframework.projectshoptoy.api.apiError;

import lombok.Data;

@Data
public class ApiError {
	private String field;
	private String message;
	public ApiError(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}
}
