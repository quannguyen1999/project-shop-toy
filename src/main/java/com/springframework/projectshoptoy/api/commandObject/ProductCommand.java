package com.springframework.projectshoptoy.api.commandObject;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
//objetct command để tránh vòng lặp khi sử dụng bi-direction
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo consuctor không tham số
@Data
public class ProductCommand {
	@Id
    private String productID;

    private boolean discontinued;

    @NotEmpty(message = "moTa can not empty")
    private String moTa;

    @NotEmpty(message = "productName can not empty")
    private String productName;

    @NotNull(message = "quantityInStock can not empty")
    @Min(value = 0)
    private int quantityInStock;

    @NotNull(message = "unitPrice can not empty")
    @Min(value = 0)
    private double unitPrice;
    
    private String categoryID;

    private String  supplierID;
}
