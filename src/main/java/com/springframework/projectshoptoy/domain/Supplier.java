package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class Supplier {
    @NotBlank
    @Id
    private String supplierID;

    private String phone;

    private String companyName;

    private String address;
}
