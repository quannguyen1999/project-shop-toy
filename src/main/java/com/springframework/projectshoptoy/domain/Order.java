package com.springframework.projectshoptoy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	@Id
    private String orderID;

    private LocalDate orderDate;

    @NotEmpty(message = "shipCity can't null")
    private String shipCity;

    @NotEmpty(message = "shipRegion can't null")
    private String shipRegion;

    @NotNull(message = "shippedDate can't null")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Future(message = "shippedDate can not past day")
    private LocalDate shippedDate;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;

	public Order(LocalDate orderDate, @NotEmpty(message = "shipCity can't null") String shipCity,
			@NotEmpty(message = "shipRegion can't null") String shipRegion,
			@NotNull(message = "shippedDate can't null") @Future(message = "shippedDate can not past day") LocalDate shippedDate,
			Customer customer, List<OrderDetails> orderDetails) {
		super();
		this.orderDate = orderDate;
		this.shipCity = shipCity;
		this.shipRegion = shipRegion;
		this.shippedDate = shippedDate;
		this.customer = customer;
		this.orderDetails = orderDetails;
	}
    
    
}
