package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String orderID;

    private LocalDate orderDate;

    @NotEmpty(message = "shipCity can't null")
    private String shipCity;

    @NotEmpty(message = "shipRegion can't null")
    private String shipRegion;

    @NotNull(message = "shippedDate can't null")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Future(message = "shippedDate can not past day")
    private LocalDate shippedDate;

    @DBRef
    private Customer customer;
}
