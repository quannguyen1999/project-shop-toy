package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class Category {
    @NotBlank
    @Id
    private String categoryID;

    private String categoryName;

    private String description;

    private String picture;
}
