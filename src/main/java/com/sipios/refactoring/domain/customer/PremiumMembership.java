package com.sipios.refactoring.domain.customer;

final class PremiumMembership implements Membership {
    static final PremiumMembership INSTANCE = new PremiumMembership();

    private PremiumMembership() {}

    @Override
    public double discount() {
        return 0.9;
    }

    @Override
    public int maximumPriceThreshold() {
        return 800;
    }
}
