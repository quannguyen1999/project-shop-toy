package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Customer {
    @Id
    private String customerID;
    private String address;
    private String city;
    private String email;
    private String firstName;
    private String lastName;

    @DBRef
    private Account account;
}
