package com.springframework.projectshoptoy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @NotBlank(message = "userName can't be empty")
    @Size(min = 4,message = "username must >= 4")
    @Id
    private String userName;

    @NotEmpty(message = "please provice password")
//    @Pattern(regexp="((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#%])).{1,}",message="password must have chars a-z,A-Z,@#%")
    @Size(min = 5,message = "password does not enough strong,must >=5")
    private String password;

    @NotNull(message = "please provice accType")
    private boolean accType;
}
