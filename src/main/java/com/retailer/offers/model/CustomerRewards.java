package com.retailer.offers.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewards {
    private String customerId;
    private List<MonthlyRewards> monthlyRewards;
    private Integer totalRewards;
}
