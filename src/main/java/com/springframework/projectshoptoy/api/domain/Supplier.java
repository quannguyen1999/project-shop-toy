package com.springframework.projectshoptoy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
	@Id
    private String supplierID;

    @NotEmpty
    @Pattern(regexp = "[0-9]{10}+",message = "phone invalid (must have length 10 number)")
    private String phone;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String address;
    
    //orpahnRemoval đảm bảo toàn vẹn dữ liệu khi product đã xóa ,thì supplier cũng phải cập nhập lại
    @OneToMany(mappedBy = "supplierID",fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Product> products;

	public Supplier(
			@NotEmpty @Pattern(regexp = "[0-9]{10}+", message = "phone invalid (must have length 10 number)") String phone,
			@NotEmpty String companyName, @NotEmpty String address) {
		super();
		this.phone = phone;
		this.companyName = companyName;
		this.address = address;
	}
	
    
}
