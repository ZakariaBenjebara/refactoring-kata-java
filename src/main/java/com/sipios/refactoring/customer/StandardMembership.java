package com.sipios.refactoring.customer;

final class StandardMembership implements Membership {
    static final StandardMembership INSTANCE = new StandardMembership();

    private StandardMembership() {}

    @Override
    public double discount() {
        return 1;
    }

    @Override
    public int maximumPriceThreshold() {
        return 200;
    }
}
