package com.sipios.refactoring.service;

import com.sipios.refactoring.domain.customer.MembershipPriceThresholdValidator;
import com.sipios.refactoring.domain.order.Order;
import com.sipios.refactoring.domain.order.OrderItem;
import com.sipios.refactoring.domain.order.ProductRebate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static com.sipios.refactoring.domain.customer.CustomerMembershipFactory.createFrom;

@Component
public class ShoppingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingService.class);

    private final Supplier<LocalDate> nowSupplier;
    private final List<ProductRebate> rebates;

    @Autowired
    public ShoppingService(Supplier<LocalDate> nowSupplier,
                           List<ProductRebate> rebates) {
        this.nowSupplier = nowSupplier;
        this.rebates = rebates;
    }

    public double calculateTotalPrice(String customerType,
                                      List<OrderItem> orderItems) {
        var customerMembership = createFrom(customerType);
        var priceThresholdValidator = new MembershipPriceThresholdValidator(customerMembership);
        var order = new Order(orderItems,
            customerMembership, nowSupplier.get(), rebates);
        try {
            double totalPrice = order.totalPrice();
            priceThresholdValidator.check(totalPrice);
            return totalPrice;
        } catch (Exception e) {
            LOGGER.error("Error occur when calculating total price: ", e);
            throw e;
        }
    }
}
