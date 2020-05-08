package com.springframework.projectshoptoy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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

	public Supplier(
			@NotEmpty @Pattern(regexp = "[0-9]{10}+", message = "phone invalid (must have length 10 number)") String phone,
			@NotEmpty String companyName, @NotEmpty String address) {
		super();
		this.phone = phone;
		this.companyName = companyName;
		this.address = address;
	}
    
    
}
