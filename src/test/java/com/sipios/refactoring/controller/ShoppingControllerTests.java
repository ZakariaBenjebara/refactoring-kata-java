package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dto.OrderItem;
import com.sipios.refactoring.dto.OrderRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingControllerTests extends UnitTest {

    private ShoppingController controller = new ShoppingController(() ->
        LocalDate.ofInstant(Instant.parse("2021-11-03T20:00:00.00Z"),
            ZoneId.of("Europe/Paris"))
    );


    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new OrderRequest(new OrderItem[]{}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void shouldCalculateTShitPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("TSHIRT", 2)},
            "STANDARD_CUSTOMER"));
        assertEquals("60.0", price);
    }

    @Test
    void shouldCalculateTShitPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("TSHIRT", 2)},
            "PREMIUM_CUSTOMER"));
        assertEquals("54.0", price);
    }

    @Test
    void shouldCalculateTShitPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("TSHIRT", 2)},
            "PLATINUM_CUSTOMER"));
        assertEquals("30.0", price);
    }

    @Test
    void shouldCalculateDressPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("DRESS", 2)},
            "STANDARD_CUSTOMER"));
        assertEquals("100.0", price);
    }

    @Test
    void shouldCalculateDressPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("DRESS", 2)},
            "PREMIUM_CUSTOMER"));
        assertEquals("90.0", price);
    }

    @Test
    void shouldCalculateDressPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("DRESS", 2)},
            "PLATINUM_CUSTOMER"));
        assertEquals("50.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("JACKET", 2)},
            "STANDARD_CUSTOMER"));
        assertEquals("200.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("JACKET", 2)},
            "PREMIUM_CUSTOMER"));
        assertEquals("180.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("JACKET", 2)},
            "PLATINUM_CUSTOMER"));
        assertEquals("100.0", price);
    }

    @Test
    void shouldApplyWinterDiscountJACKETPriceForPlatinumCustomer() {
        controller = new ShoppingController(() ->
            LocalDate.ofInstant(Instant.parse("2021-01-09T20:00:00.00Z"),
                ZoneId.of("Europe/Paris")));
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {
                new OrderItem("JACKET", 15),
                new OrderItem("DRESS", 10),
            },
            "PLATINUM_CUSTOMER"));
        assertEquals("875.0", price);
    }

    @Test
    void shouldApplySummerDiscountJACKETPriceForStandardCustomer() {
        controller = new ShoppingController(() ->
            LocalDate.ofInstant(Instant.parse("2021-06-09T20:00:00.00Z"),
                ZoneId.of("Europe/Paris")));
        String price = controller.getPrice(new OrderRequest(new OrderItem[]
            {
                new OrderItem("TSHIRT", 2),
                new OrderItem("JACKET", 1),
            },
            "STANDARD_CUSTOMER"));
        assertEquals("150.0", price);
    }

    @Test
    void shouldThrowExceptionWhenStandardCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(
            new OrderItem[]
                {new OrderItem("JACKET", 8)
                },
            "STANDARD_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (800.0) is too high for standard customer\"", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPremiumCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("JACKET", 7),
                new OrderItem("DRESS", 5),
                new OrderItem("TSHIRT", 6)
            },
            "PREMIUM_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (1017.0) is too high for premium customer\"", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPlatinumCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(new OrderItem[]
            {new OrderItem("JACKET", 25),
                new OrderItem("DRESS", 27),
                new OrderItem("TSHIRT", 25)
            },
            "PLATINUM_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (2300.0) is too high for platinum customer\"", exception.getMessage());
    }
}
