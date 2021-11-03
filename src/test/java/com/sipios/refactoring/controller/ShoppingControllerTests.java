package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new OrderRequest(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void shouldCalculateTShitPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("TSHIRT", 2) },
            "STANDARD_CUSTOMER"));
        assertEquals("60.0", price);
    }

    @Test
    void shouldCalculateTShitPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("TSHIRT", 2) },
            "PREMIUM_CUSTOMER"));
        assertEquals("54.0", price);
    }

    @Test
    void shouldCalculateTShitPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("TSHIRT", 2) },
            "PLATINUM_CUSTOMER"));
        assertEquals("30.0", price);
    }

    @Test
    void shouldCalculateDressPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("DRESS", 2) },
            "STANDARD_CUSTOMER"));
        assertEquals("100.0", price);
    }

    @Test
    void shouldCalculateDressPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("DRESS", 2) },
            "PREMIUM_CUSTOMER"));
        assertEquals("90.0", price);
    }

    @Test
    void shouldCalculateDressPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("DRESS", 2) },
            "PLATINUM_CUSTOMER"));
        assertEquals("50.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForStandardCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("JACKET", 2) },
            "STANDARD_CUSTOMER"));
        assertEquals("200.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForPremiumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("JACKET", 2) },
            "PREMIUM_CUSTOMER"));
        assertEquals("180.0", price);
    }

    @Test
    void shouldCalculateJACKETPriceForPlatinumCustomer() {
        String price = controller.getPrice(new OrderRequest(new Item[]
            { new Item("JACKET", 2) },
            "PLATINUM_CUSTOMER"));
        assertEquals("100.0", price);
    }

    @Test
    void shouldThrowExceptionWhenStandardCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(
                new Item[]
                    {new Item("JACKET", 8)
                    },
                "STANDARD_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (800.0) is too high for standard customer\"", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPremiumCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(new Item[]
                {new Item("JACKET", 7),
                    new Item("DRESS", 5),
                    new Item("TSHIRT", 6)
                },
                "PREMIUM_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (1017.0) is too high for premium customer\"", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPlatinumCustomerPriceThresholdExceed() {
        Exception exception = assertThrows(Exception.class, () -> controller.getPrice(new OrderRequest(new Item[]
                {new Item("JACKET", 25),
                    new Item("DRESS", 27),
                    new Item("TSHIRT", 25)
                },
                "PLATINUM_CUSTOMER")));
        assertEquals("400 BAD_REQUEST \"Price (2300.0) is too high for platinum customer\"", exception.getMessage());
    }
}
