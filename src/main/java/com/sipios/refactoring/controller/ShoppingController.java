package com.sipios.refactoring.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.sipios.refactoring.customer.CustomerMembershipFactory;
import com.sipios.refactoring.dto.OrderItem;
import com.sipios.refactoring.dto.OrderRequest;
import com.sipios.refactoring.order.Order;
import com.sipios.refactoring.order.Product;
import com.sipios.refactoring.order.ProductFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.sipios.refactoring.order.ProductFactory.createFrom;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    private static final String EMPTY_PRICE = "0";
    private static final String STANDARD_CUSTOMER_TYPE = "STANDARD_CUSTOMER";
    private static final String PREMIUM_CUSTOMER_TYPE = "PREMIUM_CUSTOMER";
    private static final String PLATINUM_CUSTOMER_TYPE = "PLATINUM_CUSTOMER";

    private static final int PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD = 800;
    private static final int PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD = 2000;
    private static final int STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD = 200;

    private Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);

    private final Supplier<LocalDate> nowSupplier;

    @Autowired
    public ShoppingController(Supplier<LocalDate> nowSupplier) {
        this.nowSupplier = nowSupplier;
    }

    @PostMapping
    public String getPrice(@RequestBody OrderRequest orderRequest) {
        if (null == orderRequest || orderRequest.getItems() == null) {
            return EMPTY_PRICE;
        }

        String customerType = orderRequest.getType();

        // Compute discountRate for customer
        var customerMembership = CustomerMembershipFactory.createFrom(customerType);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        var order = new Order(mapOrderItems(orderRequest.getItems()), customerMembership, nowSupplier.get());
        try {
            checkTotalPriceThresholdForCustomer(customerType, order.totalPrice());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(order.totalPrice());
    }

    private static List<com.sipios.refactoring.order.OrderItem> mapOrderItems(OrderItem[] items) {
        return Arrays.stream(items)
            .map(orderItemDto -> new com.sipios.refactoring.order.OrderItem(
                createFrom(orderItemDto.getType()), orderItemDto.getNb()))
            .collect(toList());
    }

    private void checkTotalPriceThresholdForCustomer(String customerType,
                                                     double totalPrice) throws Exception {
        if (PREMIUM_CUSTOMER_TYPE.equals(customerType)) {
            if (totalPrice > PREMIUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for premium customer");
            }
        } else if (PLATINUM_CUSTOMER_TYPE.equals(customerType)) {
            if (totalPrice > PLATINUM_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for platinum customer");
            }
        } else {
            if (totalPrice > STANDARD_CUSTOMER_MAX_PRICE_THRESHOLD) {
                throw new Exception("Price (" + totalPrice + ") is too high for standard customer");
            }
        }
    }
}
