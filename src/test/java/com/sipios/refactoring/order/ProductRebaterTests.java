package com.sipios.refactoring.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductRebaterTests {

    @Test
    void shouldApplyRebateForJacketProduct() {
        ProductRebater productRebater = new ProductRebater(List.of(
            new ProductRebate("JACKET", 0.9),
            new ProductRebate("DRESS", 0.8)
        ));

        double price = productRebater.applyRebates(new ProductName("JACKET"), 100);
        Assertions.assertThat(price).isEqualTo(90);
    }

    @Test
    void shouldApplyRebateForDressProduct() {
        ProductRebater productRebater = new ProductRebater(List.of(
            new ProductRebate("JACKET", 0.9),
            new ProductRebate("DRESS", 0.8)
        ));

        double price = productRebater.applyRebates(new ProductName("DRESS"), 360);
        Assertions.assertThat(price).isEqualTo(288.0);
    }

    @Test
    void shouldIgnoreRebateWhenUnknownProduct() {
        ProductRebater productRebater = new ProductRebater(List.of(
            new ProductRebate("JACKET", 0.9),
            new ProductRebate("DRESS", 0.8)
        ));

        double price = productRebater.applyRebates(new ProductName("TSHIRT"), 39);
        Assertions.assertThat(price).isEqualTo(39);
    }

    @Test
    void shouldIgnoreRebateForEmptyRebater() {
        ProductRebater productRebater = new ProductRebater();

        double price = productRebater.applyRebates(new ProductName("TSHIRT"), 19);
        Assertions.assertThat(price).isEqualTo(19);
    }

}
