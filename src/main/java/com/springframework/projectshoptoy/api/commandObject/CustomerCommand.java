package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.springframework.projectshoptoy.api.domain.Account;
import com.springframework.projectshoptoy.api.domain.Order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class CustomerCommand {
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

	//hide property in swagger-ui
//	@JsonIgnore
	@ApiModelProperty(position = 6)
	private List<OrderCommand> listOrder;


}
