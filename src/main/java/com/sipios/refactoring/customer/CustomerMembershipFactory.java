package com.sipios.refactoring.customer;

public final class CustomerMembershipFactory {
    private static final String STANDARD_CUSTOMER_TYPE = "STANDARD_CUSTOMER";
    private static final String PREMIUM_CUSTOMER_TYPE = "PREMIUM_CUSTOMER";
    private static final String PLATINUM_CUSTOMER_TYPE = "PLATINUM_CUSTOMER";

    public static Membership createFrom(String customerType) {
        switch (customerType) {
            case STANDARD_CUSTOMER_TYPE:
                return StandardMembership.INSTANCE;
            case PREMIUM_CUSTOMER_TYPE:
                return PremiumMembership.INSTANCE;
            case PLATINUM_CUSTOMER_TYPE:
                return PlatinumMembership.INSTANCE;
            default:
                throw new InvalidMembershipException(customerType);
        }
    }

    private CustomerMembershipFactory() {}
}
