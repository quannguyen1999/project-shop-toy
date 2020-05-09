package com.springframework.projectshoptoy.domain;

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

//    @NotEmpty(message = "please provide email")
////    @Pattern(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",message = "email invalid")
//    private String email;

    @NotEmpty(message = "please provide firstName")
    @Size(min = 3,message = "firstName must larger 3")
    private String firstName;

    @NotEmpty(message = "please provide lastName")
    @Size(min = 3,message = "lastName must larger 3")
    private String lastName;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "username")
    private Account account;
    
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
