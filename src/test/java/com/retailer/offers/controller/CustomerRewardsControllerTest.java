package com.retailer.offers.controller;

import com.retailer.offers.model.CustomerPurchases;
import com.retailer.offers.model.CustomerRewards;
import com.retailer.offers.model.TransactionRecord;
import com.retailer.offers.service.CustomerRewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRewardsControllerUnitTest {

    @InjectMocks
    private CustomerRewardsController controller;

    @Mock
    private CustomerRewardsService customerRewardsService;

    private TransactionRecord createValidTransactionRecord() {
        CustomerPurchases purchase = new CustomerPurchases();
        purchase.setAmount(100.0);
        purchase.setPurchaseDate(LocalDate.of(2024, 5, 1));

        TransactionRecord record = new TransactionRecord();
        record.setCustomerId("CUST123");
        record.setCustomerPurchases(List.of(purchase));
        return record;
    }

    @Test
    void testGetCustomerRewardsForAllCustomers() {
        List<TransactionRecord> input = List.of(createValidTransactionRecord());
        List<CustomerRewards> expected = List.of(new CustomerRewards());

        when(customerRewardsService.getCustomerRewardsList(input)).thenReturn(expected);

        ResponseEntity<List<CustomerRewards>> response = controller.getCustomerRewardsForAllCustomers(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(customerRewardsService).getCustomerRewardsList(input);
    }

    @Test
    void testGetCustomerRewardsByCustomerId() {
        List<TransactionRecord> input = List.of(createValidTransactionRecord());
        CustomerRewards expected = new CustomerRewards();

        when(customerRewardsService.getCustomerRewards(input, "CUST123")).thenReturn(expected);

        ResponseEntity<CustomerRewards> response = controller.getCustomerRewardsByCustomerId("CUST123", input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(customerRewardsService).getCustomerRewards(input, "CUST123");
    }
}


