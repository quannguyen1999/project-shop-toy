package com.springframework.projectshoptoy.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document
public class Account {
    @NotBlank(message = "id can't be empty")
    @Id
    private String userName;

    @NotEmpty(message = "please provice password")
    private String password;

    @NotNull(message = "please provice accType")
    private boolean accType;
}
