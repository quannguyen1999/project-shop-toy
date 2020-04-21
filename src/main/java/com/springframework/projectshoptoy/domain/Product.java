package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Data
@Document
public class Product {

    @Id
    private String productID;

    private boolean discontinued;

    @NotEmpty
    private String moTa;

    @NotEmpty
    private String productName;

    @NotNull
    @Min(value = 0)
    private int quantityInStock;

    @NotNull
    @Min(value = 0)
    private double unitPrice;

    @DBRef
    private Category category;

    @DBRef
    private Supplier supplier;
}
