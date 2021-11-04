package com.sipios.refactoring.domain.customer;

public final class MembershipPriceThresholdValidator {
    private final Membership membership;

    public MembershipPriceThresholdValidator(Membership membership) {
        this.membership = membership;
    }

    public void check(double price) {
        if (price > membership.maximumPriceThreshold()) {
            throw new MembershipMaximumPriceExceeded(membership, price);
        }
    }
}
