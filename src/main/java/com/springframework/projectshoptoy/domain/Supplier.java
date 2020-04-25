package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Document
public class Supplier {
    @Id
    private String supplierID;

    @NotEmpty
    @Pattern(regexp = "[0-9]{10}+",message = "phone invalid (must have length 10 number)")
    private String phone;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String address;
}
