package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String orderID;

    private LocalDate orderDate;

    @NotEmpty
    private String shipCity;

    @NotEmpty
    private String shipRegion;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDate shippedDate;

    @DBRef
    private Customer customer;
}
