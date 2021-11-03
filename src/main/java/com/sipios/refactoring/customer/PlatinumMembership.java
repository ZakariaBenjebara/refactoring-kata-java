package com.sipios.refactoring.customer;

final class PlatinumMembership implements CustomerMembership {
    static final PlatinumMembership INSTANCE = new PlatinumMembership();

    private PlatinumMembership() {}

    @Override
    public double discount() {
        return 0.5;
    }
}
