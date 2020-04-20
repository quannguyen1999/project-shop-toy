package com.springframework.projectshoptoy.domain;

import lombok.Data;

@Data
public class ErrorException {
    private String error;
    private String status;
}
