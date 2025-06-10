package com.retailer.offers.service;

import com.retailer.offers.exceptions.CustomerNotFoundException;
import com.retailer.offers.model.CustomerPurchases;
import com.retailer.offers.model.CustomerRewards;
import com.retailer.offers.model.TransactionRecord;
import com.retailer.offers.util.Utility;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRewardsServiceTest {

    private CustomerRewardsService customerRewardsService;

    @BeforeEach
    void setUp() {
        customerRewardsService = new CustomerRewardsService();
    }

    private TransactionRecord buildTransaction(String customerId, double... amounts) {
        List<CustomerPurchases> purchases =
                java.util.Arrays.stream(amounts)
                        .mapToObj(amount -> {
                            CustomerPurchases cp = new CustomerPurchases();
                            cp.setAmount(amount);
                            cp.setPurchaseDate(LocalDate.now().minusDays(10));
                            return cp;
                        }).toList();

        TransactionRecord record = new TransactionRecord();
        record.setCustomerId(customerId);
        record.setCustomerPurchases(purchases);
        return record;
    }

    @Test
    void testGetCustomerRewardsList_returnsValidRewards() {
        TransactionRecord record = buildTransaction("CUST001", 120, 75, 40);

        try (MockedStatic<Utility> utility = org.mockito.Mockito.mockStatic(Utility.class)) {
            utility.when(Utility::getLastThreeMonthNames).thenReturn(List.of("June", "May", "April"));
            utility.when(() -> Utility.getMonthNameFromDate(org.mockito.ArgumentMatchers.any()))
                    .thenReturn("June");
            utility.when(() -> Utility.calculateRewardPoints(120)).thenReturn(90);
            utility.when(() -> Utility.calculateRewardPoints(75)).thenReturn(50);
            utility.when(() -> Utility.calculateRewardPoints(40)).thenReturn(10);
            utility.when(() -> Utility.isPurchaseInLastThreeMonths(org.mockito.ArgumentMatchers.any()))
                    .thenReturn(true);

            List<CustomerRewards> rewards = customerRewardsService.getCustomerRewardsList(List.of(record));

            assertEquals(1, rewards.size());
            CustomerRewards cr = rewards.get(0);
            assertEquals("CUST001", cr.getCustomerId());
            assertEquals(150, cr.getTotalRewards());
            assertEquals(3, cr.getMonthlyRewards().size());
        }
    }

    @Test
    void testGetCustomerRewardsByCustomerId_returnsCorrectCustomer() {
        TransactionRecord record1 = buildTransaction("CUST001", 100);
        TransactionRecord record2 = buildTransaction("CUST002", 50);

        try (MockedStatic<Utility> utility = org.mockito.Mockito.mockStatic(Utility.class)) {
            utility.when(Utility::getLastThreeMonthNames).thenReturn(List.of("June", "May", "April"));
            utility.when(() -> Utility.getMonthNameFromDate(org.mockito.ArgumentMatchers.any()))
                    .thenReturn("June");
            utility.when(() -> Utility.calculateRewardPoints(100)).thenReturn(70);
            utility.when(() -> Utility.isPurchaseInLastThreeMonths(org.mockito.ArgumentMatchers.any()))
                    .thenReturn(true);

            CustomerRewards result = customerRewardsService.getCustomerRewards(List.of(record1, record2), "CUST001");

            assertEquals("CUST001", result.getCustomerId());
            assertEquals(70, result.getTotalRewards());
        }
    }

    @Test
    void testGetCustomerRewardsByCustomerId_customerNotFound() {
        TransactionRecord record1 = buildTransaction("CUST001", 100);

        CustomerRewardsService service = new CustomerRewardsService();

        Assertions.assertThrows(
                CustomerNotFoundException.class,
                () -> service.getCustomerRewards(List.of(record1), "CUST002")
        );

    }
}
