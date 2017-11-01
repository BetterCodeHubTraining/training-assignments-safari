package eu.sig.safari.exercises.automate_tests.banking.stubs;

public class Money {

    private int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public boolean greaterThan(int otherAmount) {
        return this.amount > otherAmount;
    }

}
