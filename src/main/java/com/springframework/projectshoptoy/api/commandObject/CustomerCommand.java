package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
//objetct command để tránh vòng lặp khi sử dụng bi-direction
//@Data để khởi tạo getter,setter,constructor mặc định 
//@NoArgsConstructor để tạo constructor không tham số
@NoArgsConstructor
@Data
public class CustomerCommand {
	//@ApiModel property sắp xếp các thuộc tính 
	@ApiModelProperty(position = 0)
	private String customerID;

	@ApiModelProperty(position = 1)
	@NotEmpty(message = "please provide address")
	@Size(min = 10,message = "address must larger 10")
	private String address;

	@ApiModelProperty(position = 2)
	@NotEmpty(message = "please provide city")
	@Size(min = 5,message = "city must larger 5")
	private String city;

	@ApiModelProperty(position = 3)
	@NotEmpty(message = "please provide firstName")
	@Size(min = 3,message = "firstName must larger 3")
	private String firstName;

	@ApiModelProperty(position = 4)
	@NotEmpty(message = "please provide lastName")
	@Size(min = 3,message = "lastName must larger 3")
	private String lastName;

	//vị trí ưu tiên swagger-ui là thứ 6
	@ApiModelProperty(position = 5)
	private String userName;

	@ApiModelProperty(position = 6)
	private List<OrderCommand> listOrder;
}
