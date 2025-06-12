package com.retailer.offers.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {
    @NotBlank(message = "Customer Id is required")
    @Valid
    private String customerId;

    @NotEmpty(message = "customer purchases are required")
    @Valid
    private List<CustomerPurchases> customerPurchases;
}
