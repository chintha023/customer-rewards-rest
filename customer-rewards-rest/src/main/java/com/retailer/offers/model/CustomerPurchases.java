package com.retailer.offers.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPurchases {

    @Positive(message = "Amount should be greater than 0")
    private double amount;

    @NotNull(message = "Purchase date is required")
    @Past(message = "Purchase date should be past")
    private LocalDate purchaseDate;
}
