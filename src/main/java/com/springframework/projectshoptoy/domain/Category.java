package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Document
public class Category {
    @Id
    private String categoryID;

    @NotEmpty(message = "categoryName not valid")
    private String categoryName;

    @NotEmpty(message = "description not valid")
    private String description;

    @NotEmpty(message = "picture not valid")
    private String picture;
}
