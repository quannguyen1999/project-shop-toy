package com.springframework.projectshoptoy.api.commandObject;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.springframework.projectshoptoy.api.domain.Product;

import lombok.Data;
import lombok.NoArgsConstructor;
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
