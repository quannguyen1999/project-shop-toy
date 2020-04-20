package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class OrderDetails {
    @NotBlank
    @Id
    private String orderID;

    private float discount;

    private int quanity;

    private int totalAmount;

    @DBRef
    private Product product;
}
