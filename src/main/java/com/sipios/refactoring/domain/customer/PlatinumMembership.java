package com.sipios.refactoring.domain.customer;

final class PlatinumMembership implements Membership {
    static final PlatinumMembership INSTANCE = new PlatinumMembership();

    private PlatinumMembership() {}

    @Override
    public double discount() {
        return 0.5;
    }

    @Override
    public int maximumPriceThreshold() {
        return 2000;
    }
}
