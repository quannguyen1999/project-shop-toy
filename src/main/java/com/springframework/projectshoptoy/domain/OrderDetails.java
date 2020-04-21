package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document
public class OrderDetails {
    @Id
    private String orderDetailID;

    @NotNull
    @Min(value = 0)
    private float discount;

    @NotNull
    @Min(value = 0)
    private int quanity;

    private int totalAmount;

    @DBRef
    private Product product;

    @DBRef
    private Order order;
}
