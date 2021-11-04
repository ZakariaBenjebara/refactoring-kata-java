package com.sipios.refactoring.domain.order;

import com.sipios.refactoring.domain.customer.Membership;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;

public final class Order {
    private static final int MAXIMUM_DISCOUNT_DAY_OF_MONTH = 15;
    private static final int MINIMUM_DISCOUNT_DAY_OF_MONTH = 5;

    private final List<OrderItem> items;
    private final Membership membership;
    private final ProductRebater productRebater;

    public Order(List<OrderItem> items,
                 Membership membership,
                 LocalDate creationDate,
                 List<ProductRebate> rebates) {
        this.items = Collections.unmodifiableList(items);
        this.productRebater = createRebatesFor(creationDate, rebates);
        this.membership = membership;
    }

    public double totalPrice() {
        return items.stream()
            .mapToDouble(applyDiscountToItem(productRebater))
            .sum();
    }

    private ToDoubleFunction<OrderItem> applyDiscountToItem(ProductRebater productRebater)
    {
        return orderItem -> orderItem.calculatePrice(productRebater) * membership.discount();
    }

    private static ProductRebater createRebatesFor(LocalDate currentDate,
                                                   List<ProductRebate> rebates) {
        if (isWithinDiscountPeriod(currentDate, Month.JANUARY)
            || isWithinDiscountPeriod(currentDate, Month.JUNE)) {
            return new ProductRebater(rebates);
        }
        return new ProductRebater();
    }

    private static boolean isWithinDiscountPeriod(LocalDate currentDate,
                                                  Month month)
    {
        return currentDate.getDayOfMonth() < MAXIMUM_DISCOUNT_DAY_OF_MONTH
            && currentDate.getDayOfMonth() > MINIMUM_DISCOUNT_DAY_OF_MONTH
            && currentDate.getMonth() == month;
    }
}
