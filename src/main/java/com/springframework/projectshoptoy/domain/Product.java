package com.springframework.projectshoptoy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product{
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierID")
    private Supplier supplier;
    
	public Product(boolean discontinued, @NotEmpty(message = "moTa can not empty") String moTa,
			@NotEmpty(message = "productName can not empty") String productName,
			@NotNull(message = "quantityInStock can not empty") @Min(0) int quantityInStock,
			@NotNull(message = "unitPrice can not empty") @Min(0) double unitPrice, Category category,
			Supplier supplier) {
		super();
		this.discontinued = discontinued;
		this.moTa = moTa;
		this.productName = productName;
		this.quantityInStock = quantityInStock;
		this.unitPrice = unitPrice;
		this.category = category;
		this.supplier = supplier;
	}
    
    
}
