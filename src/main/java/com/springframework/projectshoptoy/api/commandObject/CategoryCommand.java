package com.springframework.projectshoptoy.api.commandObject;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springframework.projectshoptoy.api.domain.Product;

import lombok.Data;
@Data
public class CategoryCommand {
	private String categoryID;

	@NotEmpty(message = "categoryName not valid")
	private String categoryName;

	@NotEmpty(message = "description not valid")
	private String description;

	@NotEmpty(message = "picture not valid")
	private String picture;

	//hide property in swagger-ui
//	@JsonIgnore
	private List<ProductCommand> productCommands;

}
