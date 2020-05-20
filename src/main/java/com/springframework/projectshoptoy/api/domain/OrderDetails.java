package com.springframework.projectshoptoy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable{
    @NotNull(message = "discount can't null")
    @Min(value = 0)
    private float discount;

    @NotNull(message = "quanity can't null")
    @Min(value = 0)
    private int quanity;

    private int totalAmount;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID")
    private Product product;
}
