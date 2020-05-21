package com.springframework.projectshoptoy.api.domain;

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
//@Entity để thêm 1 collection vào mongodb
//@Table để tạo tên bảng
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
    
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "categoryID")
    private Category categoryID;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "supplierID")
    private Supplier supplierID;
    
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
		this.categoryID = category;
		this.supplierID = supplier;
	}

	public Product() {
		super();
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public boolean isDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Category getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Category categoryID) {
		this.categoryID = categoryID;
	}

	public Supplier getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Supplier supplierID) {
		this.supplierID = supplierID;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", discontinued=" + discontinued + ", moTa=" + moTa
				+ ", productName=" + productName + ", quantityInStock=" + quantityInStock + ", unitPrice=" + unitPrice
				+ "]";
	}
	
	
    
    
}
