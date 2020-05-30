package com.springframework.projectshoptoy.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.springframework.projectshoptoy.api.apiError.ApiError;
import com.springframework.projectshoptoy.api.apiError.CustomRespone;

import lombok.extern.slf4j.Slf4j;
@Component
public class CustomerValidateAndStatusImpl implements CustomValidateAndStatus{
		
	public ResponseEntity<CustomRespone> checkCustomRespon(CustomRespone customRespone){
		int code=customRespone.getCode();
		ResponseEntity<CustomRespone> respon=null;
		switch (code) {
		case 1:
		case 5:
		case 2:
		case 12:
			respon=new ResponseEntity<CustomRespone>(customRespone, HttpStatus.BAD_REQUEST);
			break;
		case 3:
			respon=new ResponseEntity<CustomRespone>(customRespone, HttpStatus.NOT_FOUND);
			break;
		case 6:
			respon=new ResponseEntity<CustomRespone>(customRespone, HttpStatus.CREATED);
			break;
		default:
			respon=new ResponseEntity<CustomRespone>(customRespone, HttpStatus.OK);
			break;
		}
		return respon;
	}

	@Override
	public CustomRespone getListApiErrorAndBindingResult(Errors errors, BindingResult bindingResult) {
		boolean result=true;
		//check bên @annotation trước
		List<ApiError> listApiError=(List<ApiError>) getListApiError(errors).getObject();
		//không có lỗi thì check tiếp
		if(listApiError.size()<=0) {
			//check bên bindingresult
			listApiError=(List<ApiError>) getListApiBindingResult(bindingResult).getObject();
		}
		return listApiError.size()>0 ? new CustomRespone(12,"Lỗi validate",listApiError) : new CustomRespone(13, "Validate hợp lệ", listApiError);
	}


	@Override
	public CustomRespone getListApiError(Errors errors) {
		List<ApiError> listApiError=new ArrayList<ApiError>();
		ApiError apiError=null;
		if( errors.hasErrors()) {
			FieldError fieldError=null;
			for(Object object: errors.getAllErrors()) {
				if(object instanceof FieldError) {
					fieldError=(FieldError) object;
					apiError=new ApiError(fieldError.getField(), fieldError.getDefaultMessage());
					listApiError.add(apiError);
				}
			}
		}
		return listApiError.size()>0 ? new CustomRespone(12,"Lỗi validate",listApiError) : new CustomRespone(13, "Validate hợp lệ", listApiError);
	}

	@Override
	public CustomRespone getListApiBindingResult(BindingResult bindingResult) {
		List<ApiError> listApiError=new ArrayList<ApiError>();
		ApiError apiError=null;
		if(bindingResult.hasErrors()) {
			FieldError fieldError=null;
			for(Object object:bindingResult.getAllErrors()) {
				if(object instanceof FieldError) {
					fieldError=(FieldError) object;
					apiError=new ApiError(fieldError.getField(), fieldError.getDefaultMessage());
					listApiError.add(apiError);
				}
			}
		}
		return listApiError.size()>0 ? new CustomRespone(12,"Lỗi validate",listApiError) : new CustomRespone(13, "Validate hợp lệ", listApiError);
	}
}
