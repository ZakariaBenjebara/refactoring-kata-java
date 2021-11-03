package com.sipios.refactoring.customer;

final class StandardMembership implements CustomerMembership {
    static final StandardMembership INSTANCE = new StandardMembership();

    private StandardMembership() {}

    @Override
    public double discount() {
        return 1;
    }
}
