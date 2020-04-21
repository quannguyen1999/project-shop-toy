package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Document
public class Supplier {
    @Id
    private String supplierID;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String address;
}
