package com.retailer.offers.service;

import com.retailer.offers.exceptions.CustomerNotFoundException;
import com.retailer.offers.model.CustomerPurchases;
import com.retailer.offers.model.CustomerRewards;
import com.retailer.offers.model.MonthlyRewards;
import com.retailer.offers.model.TransactionRecord;
import com.retailer.offers.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomerRewardsService {

    public List<CustomerRewards> getCustomerRewardsList(List<TransactionRecord> transactionsRecords) {
        List<CustomerRewards> customerRewardsList = new ArrayList<>();
        transactionsRecords.forEach(transactionRecord -> {
            customerRewardsList.add(prepareCustomerRewards(transactionRecord));
        });
        return customerRewardsList;
    }

    public CustomerRewards getCustomerRewards(List<TransactionRecord> transactionRecords, String customerId) {
        List<TransactionRecord> transactionRecordsList = transactionRecords.stream()
                .filter(transactionRecord -> transactionRecord.getCustomerId().equals(customerId)).toList();
        if(transactionRecordsList.isEmpty()){
            log.info("No transaction records found for customer id: {}", customerId);
            throw new CustomerNotFoundException("No transaction records found for customer id: " + customerId);
        }
       return prepareCustomerRewards(transactionRecordsList.get(0));
    }

    private CustomerRewards prepareCustomerRewards(TransactionRecord transactionRecord) {
        CustomerRewards customerReward = new CustomerRewards();
        customerReward.setCustomerId(transactionRecord.getCustomerId());
        int customerTotalRewardPoints = getTotalRewardsForLastThreeMonths(transactionRecord.getCustomerPurchases());
        customerReward.setTotalRewards(customerTotalRewardPoints);
        customerReward.setMonthlyRewards(getMonthlyRewardsforLastThreeMonths(transactionRecord.getCustomerPurchases()));
        return customerReward;
    }


    private List<MonthlyRewards> getMonthlyRewardsforLastThreeMonths(List<CustomerPurchases> customerPurchases) {
        List<String> lastThreeMonthNames = Utility.getLastThreeMonthNames();
        List<MonthlyRewards> monthlyRewardsList = new ArrayList<>();
        lastThreeMonthNames.stream().forEach(monthName -> {
            MonthlyRewards monthlyReward = new MonthlyRewards();
            int monthlyRewardPoints = customerPurchases.stream().filter(customerPurchase ->
                    Utility.getMonthNameFromDate(customerPurchase.getPurchaseDate()).equals(monthName))
                    .mapToInt(customerPurchase -> Utility.calculateRewardPoints(customerPurchase.getAmount()))
                    .sum();
            monthlyReward.setMonth(monthName);
            monthlyReward.setRewards(monthlyRewardPoints);
            monthlyRewardsList.add(monthlyReward);
        });
        return monthlyRewardsList;
    }

    private int getTotalRewardsForLastThreeMonths(List<CustomerPurchases> customerPurchases){
       return customerPurchases.stream().filter(customerPurchase -> Utility.isPurchaseInLastThreeMonths(customerPurchase.getPurchaseDate()))
                .mapToInt(customerPurchase -> Utility.calculateRewardPoints(customerPurchase.getAmount()))
                .sum();
    }

}
