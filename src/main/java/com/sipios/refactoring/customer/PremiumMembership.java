package com.sipios.refactoring.customer;

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
