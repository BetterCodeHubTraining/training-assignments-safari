package eu.sig.safari.exercises.automate_tests.banking.stubs;

import eu.sig.safari.exercises.automate_tests.banking.CheckingAccount;

public class Transfer {
    CheckingAccount counterAccount;

    public Transfer(CheckingAccount acct1, CheckingAccount acct2, Money m) {}

    public CheckingAccount getCounterAccount() {
        return this.counterAccount;
    }
}
