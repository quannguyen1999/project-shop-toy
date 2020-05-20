package com.springframework.projectshoptoy.api.domain;

import lombok.Data;

@Data
public class ErrorException {
    private String error;
    private String status;
}
