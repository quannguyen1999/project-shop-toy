package com.springframework.projectshoptoy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo constructor không tham số
//@AllArgsCOnstructor là khởi tạo constructor có tham số
//@Entity để thêm 1 collection vào mongodb
//@Table để tạo tên bảng
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
	@Id
    private String customerID;

    @NotEmpty(message = "please provide address")
    @Size(min = 10,message = "address must larger 10")
    private String address;

    @NotEmpty(message = "please provide city")
    @Size(min = 5,message = "city must larger 5")
    private String city;

    @NotEmpty(message = "please provide firstName")
    @Size(min = 3,message = "firstName must larger 3")
    private String firstName;

    @NotEmpty(message = "please provide lastName")
    @Size(min = 3,message = "lastName must larger 3")
    private String lastName;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "username")
    private Account account;
    
    //cascade để đảm bảo khi xóa customer thì tất cả order của customer cũng phải xóa
    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private List<Order> listOrder;

	public Customer(
			@NotEmpty(message = "please provide address") @Size(min = 10, message = "address must larger 10") String address,
			@NotEmpty(message = "please provide city") @Size(min = 5, message = "city must larger 5") String city,
			@NotEmpty(message = "please provide firstName") @Size(min = 3, message = "firstName must larger 3") String firstName,
			@NotEmpty(message = "please provide lastName") @Size(min = 3, message = "lastName must larger 3") String lastName) {
		super();
		this.address = address;
		this.city = city;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	

    
    
    
}
