package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Document
public class Order {
    @NotBlank
    @Id
    private String orderID;

    private LocalDate orderDate;

    private String shipCity;

    private String shipRegion;

    @DateTimeFormat
    private LocalDate shippedDate;

    @DBRef
    private Customer customer;
}
