package com.springframework.projectshoptoy.api.domain;

import lombok.Data;
//ErrorException để gửi phản hồi về client khi xử lý bị lỗi 
//error tên lỗi 
//status:kiểu lỗi vd:404,401,...
@Data
public class ErrorException {
    private String error;
    private String status;
}
