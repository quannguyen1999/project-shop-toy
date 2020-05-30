package com.springframework.projectshoptoy.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.springframework.projectshoptoy.api.apiError.CustomRespone;

//gọi các chức năng thông qua interface
public interface CustomValidateAndStatus {
	//kiểm tra các lỗi và trả về kiểu status hợp lệ
	//các thông tin
	//	BAD_REQUEST
	//	1 Rỗng hoặc null
	//	5 trùng id hoặc name 
	//	2 password và repassword không đúng
	//	12 token hết hạn
	//	NOT_FOUND
	//	3 không tìm thấy , token không đáng tin
	//
	//	CREATED
	//	6 thêm thành công
	//
	//	OK
	//	4 gửi thành công
	//	7 xóa thành công 
	//	8 cập nhập thành công
	//	9 tìm thấy 
	//	10 danh sách 
	//	11 token đáng tin
	public ResponseEntity<CustomRespone> checkCustomRespon(CustomRespone customReponse);

	//trả về list các field không hợp lệ
	public CustomRespone getListApiErrorAndBindingResult(Errors errors,BindingResult bindingResult);
	
	//validate bên domain bằng các annotation
	public CustomRespone getListApiError(Errors errors);
	
	//validate bên validator
	public CustomRespone getListApiBindingResult(BindingResult bindingResult);
}
