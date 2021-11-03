package com.sipios.refactoring.customer;

final class PremiumMembership implements CustomerMembership {
    static final PremiumMembership INSTANCE = new PremiumMembership();

    private PremiumMembership() {}

    @Override
    public double discount() {
        return 0.9;
    }
}
