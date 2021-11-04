package com.sipios.refactoring.domain.customer;

public class MembershipMaximumPriceExceeded extends RuntimeException {

    public MembershipMaximumPriceExceeded(Membership membership, double price) {
        super("Price (" + price + ") is too high for " + asLabel(membership));
    }

    private static String asLabel(Membership membership) {
        if (membership instanceof PlatinumMembership) {
            return "platinum customer";
        }
        if (membership instanceof PremiumMembership) {
            return "premium customer";
        }
        return "standard customer";
    }
}
