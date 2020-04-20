package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class Product {
    @NotBlank
    @Id
    private String productID;

    private boolean discontinued;

    private String moTa;

    private String productName;

    private int quantityInStock;

    private double unitPrice;

    @DBRef
    private Category category;

    @DBRef
    private Supplier supplier;
}
