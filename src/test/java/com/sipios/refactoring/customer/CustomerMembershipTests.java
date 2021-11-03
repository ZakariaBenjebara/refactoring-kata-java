package com.sipios.refactoring.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.sipios.refactoring.customer.CustomerMembershipFactory.createFrom;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerMembershipTests {

    @Test
    void shouldGetDiscountForStandardCustomer() {
        var standardCustomer = createFrom("STANDARD_CUSTOMER");
        Assertions.assertThat(standardCustomer.discount()).isEqualTo(1);
    }

    @Test
    void shouldGetDiscountForPremiumCustomer() {
        var premiumCustomer = createFrom("PREMIUM_CUSTOMER");
        Assertions.assertThat(premiumCustomer.discount()).isEqualTo(0.9);
    }

    @Test
    void shouldGetDiscountForPlatinumCustomer() {
        var platinumCustomer = createFrom("PLATINUM_CUSTOMER");
        Assertions.assertThat(platinumCustomer.discount()).isEqualTo(0.5);
    }

    @Test
    void shouldThrowsInvalidCustomerMembership() {
        var exception = assertThrows(InvalidMembershipException.class,
            () -> createFrom("INVALID_CUSTOMER"));
        Assertions.assertThat(exception).hasMessage("Invalid type of membership: INVALID_CUSTOMER");
    }

}
