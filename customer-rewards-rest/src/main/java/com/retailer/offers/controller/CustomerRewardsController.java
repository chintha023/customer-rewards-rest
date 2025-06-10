package com.retailer.offers.controller;

import com.retailer.offers.model.CustomerRewards;
import com.retailer.offers.model.TransactionRecord;
import com.retailer.offers.service.CustomerRewardsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerRewardsController implements CustomerRewardsAPI {

    @Autowired
    CustomerRewardsService customerRewardsService;

    @PostMapping("/all")
    @Override
    public ResponseEntity<List<CustomerRewards>> getCustomerRewardsForAllCustomers(@Valid @RequestBody List<TransactionRecord> transactionRecords) {
        List<CustomerRewards> customerRewardsList = customerRewardsService.getCustomerRewardsList(transactionRecords);
        return ResponseEntity.ok(customerRewardsList);
    }

    @PostMapping("/{customerId}")
    @Override
    public ResponseEntity<CustomerRewards> getCustomerRewardsByCustomerId(@PathVariable(value = "customerId", required =true) String customerId,
                                                                          @Valid @RequestBody List<TransactionRecord> transactionRecords) {
        CustomerRewards customerRewards = customerRewardsService.getCustomerRewards(transactionRecords, customerId);
        return ResponseEntity.ok(customerRewards);
    }
}
