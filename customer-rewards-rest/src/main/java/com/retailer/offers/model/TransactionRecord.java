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
    @NotEmpty(message = "Customer Id is required")
    @Valid
    private String customerId;

    @NotNull
    @Valid
    private List<CustomerPurchases> customerPurchases;
}
