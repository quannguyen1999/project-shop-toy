package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springframework.projectshoptoy.api.domain.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class SupplierCommand {
	@ApiModelProperty(position = 0)
    private String supplierID;

	@ApiModelProperty(position = 1)
    @NotEmpty
    @Pattern(regexp = "[0-9]{10}+",message = "phone invalid (must have length 10 number)")
    private String phone;

	@ApiModelProperty(position = 2)
    @NotEmpty
    private String companyName;

	@ApiModelProperty(position = 3)
    @NotEmpty
    private String address;
    
    //hide property in swagger-ui
//    @JsonIgnore
	@ApiModelProperty(position = 4)
    private List<ProductCommand> productCommands;

}
