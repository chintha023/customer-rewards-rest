package com.retailer.offers.controller;

import com.retailer.offers.model.CustomerRewards;
import com.retailer.offers.model.ErrorResponse;
import com.retailer.offers.model.TransactionRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Validated
public interface CustomerRewardsAPI {

    @Operation(summary = "Get customer rewards for all customers for last three months",
            responses = {
          @ApiResponse(responseCode = "200", description = "Response including rewards for all customers", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerRewards.class))),
          @ApiResponse(responseCode = "400", description = "The request was not constructed properly, missing required fields or contains invalid values.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    }
    )
    ResponseEntity<List<CustomerRewards>> getCustomerRewardsForAllCustomers(@Valid @RequestBody List<TransactionRecord> transactionRecords);


    @Operation(summary = "Get customer rewards for a customer Id for last three months",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Response including rewards for a customer id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerRewards.class))),
                    @ApiResponse(responseCode = "400", description = "The request was not constructed properly, missing required fields or contains invalid values.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<CustomerRewards> getCustomerRewardsByCustomerId(@PathVariable(value = "customerId", required =true) String customerId, @Valid @RequestBody List<TransactionRecord> transactionRecords);

}
