package com.springframework.projectshoptoy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo constructor không tham số
//@AllArgsCOnstructor là khởi tạo constructor có tham số
//@Entity để thêm 1 collection vào mongodb
//@Table để tạo tên bảng
//@Embeddable để nhúng OrderDetails vào Order
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "discount can't null")
    @Min(value = 0)
    private float discount;

    @NotNull(message = "quanity can't null")
    @Min(value = 0)
    private int quanity;

    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
}
