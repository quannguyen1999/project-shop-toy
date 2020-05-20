package com.springframework.projectshoptoy.api.commandObject;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.springframework.projectshoptoy.api.domain.Category;
import com.springframework.projectshoptoy.api.domain.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class ProductCommand {
	@Id
    private String productID;

    private boolean discontinued;

    @NotEmpty(message = "moTa can not empty")
    private String moTa;

    @NotEmpty(message = "productName can not empty")
    private String productName;

    @NotNull(message = "quantityInStock can not empty")
    @Min(value = 0)
    private int quantityInStock;

    @NotNull(message = "unitPrice can not empty")
    @Min(value = 0)
    private double unitPrice;
    
    private String categoryID;

    private String  supplierID;

}
