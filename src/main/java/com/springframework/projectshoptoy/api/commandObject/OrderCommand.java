package com.springframework.projectshoptoy.api.commandObject;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.springframework.projectshoptoy.api.domain.OrderDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class OrderCommand {
	private String orderId;
	
    private LocalDate orderDate;

    @NotEmpty(message = "shipCity can't null")
    private String shipCity;

    @NotEmpty(message = "shipRegion can't null")
    private String shipRegion;

    @NotNull(message = "shippedDate can't null")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Future(message = "shippedDate can not past day")
    private LocalDate shippedDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<OrderDetailsCommand> orderDetails;
    
    private String customerID;
}
