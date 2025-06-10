package com.retailer.offers.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRewards {

    private String month;
    private Integer rewards;
}
