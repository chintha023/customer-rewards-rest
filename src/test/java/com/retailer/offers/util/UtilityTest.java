package com.retailer.offers.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void testCalculateRewardPoints_under50() {
        assertEquals(0, Utility.calculateRewardPoints(49.99));
    }

    @Test
    void testCalculateRewardPoints_exactly50() {
        assertEquals(0, Utility.calculateRewardPoints(50));
    }

    @Test
    void testCalculateRewardPoints_between50And100() {
        assertEquals(10, Utility.calculateRewardPoints(55));
        assertEquals(40, Utility.calculateRewardPoints(70));
    }

    @Test
    void testCalculateRewardPoints_above100() {
        assertEquals(90, Utility.calculateRewardPoints(120));
        assertEquals(110, Utility.calculateRewardPoints(130));
    }

    @Test
    void testIsPurchaseInLastThreeMonths_withInThreeMonths() {
        LocalDate recentDate = LocalDate.now().minusMonths(2);
        assertTrue(Utility.isPurchaseInLastThreeMonths(recentDate));
    }

    @Test
    void testIsPurchaseInLastThreeMonths_MoreThanThreeMonths() {
        LocalDate oldDate = LocalDate.now().minusMonths(4);
        assertFalse(Utility.isPurchaseInLastThreeMonths(oldDate));
    }

    @Test
    void testIsPurchaseInLastThreeMonths_today() {
        assertFalse(Utility.isPurchaseInLastThreeMonths(LocalDate.now()));
    }

    @Test
    void testGetLastThreeMonthNames_returnsCorrectMonths() {
        LocalDate now = LocalDate.now();
        List<String> result = Utility.getLastThreeMonthNames();

        assertEquals(3, result.size());

        assertEquals(now.minusMonths(1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), result.get(0));
        assertEquals(now.minusMonths(2).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), result.get(1));
        assertEquals(now.minusMonths(3).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), result.get(2));
    }

    @Test
    void testGetMonthNameFromDate_validMonth() {
        LocalDate date = LocalDate.of(2024, Month.MARCH, 15);
        String monthName = Utility.getMonthNameFromDate(date);
        assertEquals("March", monthName);
    }
}
