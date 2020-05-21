package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//objetct command để tránh vòng lặp khi sử dụng bi-direction
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo consuctor không tham số
@Data
public class SupplierCommand {
	//@ApiModelProperty để sắp xếp thứ tự property trong swagger-ui
	@ApiModelProperty(position = 0)
    private String supplierID;

	@ApiModelProperty(position = 1)
    @NotEmpty
    @Pattern(regexp = "[0-9]{10}+",message = "phone invalid (must have length 10 number)")
    private String phone;

	@ApiModelProperty(position = 2)
    @NotEmpty
    private String companyName;

	@ApiModelProperty(position = 3)
    @NotEmpty
    private String address;
    
	@ApiModelProperty(position = 4)
    private List<ProductCommand> productCommands;

}
