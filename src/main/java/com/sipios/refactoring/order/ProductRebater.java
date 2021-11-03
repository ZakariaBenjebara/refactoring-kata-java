package com.sipios.refactoring.order;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

final class ProductRebater {
    private final List<ProductRebate> rebates;

    ProductRebater(List<ProductRebate> rebates) {
        this.rebates = unmodifiableList(rebates);
    }

    ProductRebater() {
        this.rebates = emptyList();
    }

    double applyRebates(String productName, double price) {
        return rebates.stream()
            .filter(productRebate -> productRebate.isAllowedRebate(productName))
            .findFirst()
            .map(rebate -> rebate.apply(price))
            .orElse(price);
    }
}
