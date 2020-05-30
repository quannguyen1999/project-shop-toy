package com.springframework.projectshoptoy.api.apiError;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomRespone {
	private int code;
	private String message;
	private Object object;
	public CustomRespone(int code, String message, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.object = object;
	}
	
	
}
