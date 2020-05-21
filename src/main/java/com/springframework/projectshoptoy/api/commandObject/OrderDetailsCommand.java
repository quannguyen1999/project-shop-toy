package com.springframework.projectshoptoy.api.commandObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
//objetct command để tránh vòng lặp khi sử dụng bi-direction
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo consuctor không tham số
@Data
@NoArgsConstructor
public class OrderDetailsCommand {
	@NotNull(message = "discount can't null")
	@Min(value = 0)
	private float discount;

	@NotNull(message = "quanity can't null")
	@Min(value = 0)
	private int quanity;

	private int totalAmount;

	private String productID;
}
