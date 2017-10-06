package eu.sig.safari.exercises.duplication.banking.stubs;

import eu.sig.safari.exercises.duplication.banking.CheckingAccount;
import eu.sig.safari.exercises.duplication.banking.SavingsAccount;

public class Transfer {
    CheckingAccount counterAccount;

    public Transfer(CheckingAccount acct1, CheckingAccount acct2, Money m) {}

    public Transfer(SavingsAccount acct1, CheckingAccount acct2, Money m) {}

    public CheckingAccount getCounterAccount() {
        return this.counterAccount;
    }
}
