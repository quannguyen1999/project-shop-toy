package com.springframework.projectshoptoy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorys")
public class Category {
	@Id
    private String categoryID;

    @NotEmpty(message = "categoryName not valid")
    private String categoryName;

    @NotEmpty(message = "description not valid")
    private String description;

    @NotEmpty(message = "picture not valid")
    private String picture;

	public Category(@NotEmpty(message = "categoryName not valid") String categoryName,
			@NotEmpty(message = "description not valid") String description,
			@NotEmpty(message = "picture not valid") String picture) {
		super();
		this.categoryName = categoryName;
		this.description = description;
		this.picture = picture;
	}
    
    
}
