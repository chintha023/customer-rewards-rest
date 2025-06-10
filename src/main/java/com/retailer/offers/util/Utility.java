package com.retailer.offers.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {
    /**
     * A customer receives 2 points for every dollar spent over $100 in each transaction,
     * plus 1 point for every dollar spent between $50 and $100 in each transaction.
     * @param amount
     */
    public static int calculateRewardPoints(double amount){
        int rewardPoints = 0;
        if(amount > 100){
            rewardPoints = (int) ((amount - 100) * 2);
            rewardPoints = rewardPoints + 50;
        } else if(amount > 50){
            rewardPoints = rewardPoints + (int) ((amount - 50) * 2);
        }

        return rewardPoints;
    }

    public static boolean isPurchaseInLastThreeMonths(LocalDate purchaseDate){
        LocalDate today = LocalDate.now();
        LocalDate lastThreeMonths = today.minusMonths(3);
        return purchaseDate.isAfter(lastThreeMonths) && purchaseDate.isBefore(today);
    }

    public static List<String> getLastThreeMonthNames(){
        LocalDate today = LocalDate.now();
        List<String> lastThreeMonthNames = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            Month month = today.minusMonths(i).getMonth();
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            lastThreeMonthNames.add(monthName);
        }
        return lastThreeMonthNames;
    }

    public static String getMonthNameFromDate(LocalDate purchaseDate){
        return purchaseDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
