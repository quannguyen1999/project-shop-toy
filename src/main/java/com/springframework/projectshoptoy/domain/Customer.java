package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Document
public class Customer {
    @Id
    private String customerID;

    @NotEmpty(message = "please provide address")
    @Size(min = 10,message = "address must larger 10")
    private String address;

    @NotEmpty(message = "please provide city")
    @Size(min = 5,message = "city must larger 5")
    private String city;

    @NotEmpty(message = "please provide email")
    @Pattern(regexp = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",message = "email invalid")
    private String email;

    @NotEmpty(message = "please provide firstName")
    @Size(min = 3,message = "firstName must larger 3")
    private String firstName;

    @NotEmpty(message = "please provide lastName")
    @Size(min = 3,message = "lastName must larger 3")
    private String lastName;

    @DBRef
    private Account account;
}
