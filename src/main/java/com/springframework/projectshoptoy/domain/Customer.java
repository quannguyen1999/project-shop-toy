package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Document
public class Customer {
    @Id
    private String customerID;

    @NotEmpty(message = "please provide address")
    private String address;

    @NotEmpty(message = "please provide city")
    private String city;

    @NotEmpty(message = "please provide email")
    private String email;

    @NotEmpty(message = "please provide firstName")
    private String firstName;

    @NotEmpty(message = "please provide lastName")
    private String lastName;

    @DBRef
    private Account account;
}
