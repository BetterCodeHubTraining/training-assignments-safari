package eu.sig.safari.exercises.duplication.banking.stubs;

import eu.sig.safari.exercises.duplication.banking.CheckingAccount;

public class Accounts {

    public static CheckingAccount findAcctByNumber(String number) {
        return new CheckingAccount();
    }

    public static boolean isValid(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            sum = sum + (9 - i) * Character.getNumericValue(number.charAt(i));
        }
        return sum % 11 == 0;
    }
}
