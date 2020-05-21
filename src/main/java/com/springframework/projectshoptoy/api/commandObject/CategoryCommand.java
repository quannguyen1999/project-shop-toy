package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
//objetct command để tránh vòng lặp khi sử dụng bi-direction
//@Data để khởi tạo getter,setter,constructor mặc định 
@Data
public class CategoryCommand {
	private String categoryID;

	@NotEmpty(message = "categoryName not valid")
	private String categoryName;

	@NotEmpty(message = "description not valid")
	private String description;

	@NotEmpty(message = "picture not valid")
	private String picture;

	private List<ProductCommand> productCommands;
}
