/** 
 * NOTICE: 
 * - This file has been modified by the Software Improvement Group (SIG) to adapt it for training purposes
 * - The original file can be found here: https://github.com/oreillymedia/building_maintainable_software
 * 
 */
package eu.sig.safari.exercises.automate_tests.banking;

import eu.sig.safari.exercises.automate_tests.banking.stubs.Accounts;
import eu.sig.safari.exercises.automate_tests.banking.stubs.BusinessException;
import eu.sig.safari.exercises.automate_tests.banking.stubs.Money;
import eu.sig.safari.exercises.automate_tests.banking.stubs.Transfer;

public class CheckingAccount {
    private int transferLimit = 100;

    public Transfer makeTransfer(String counterAccount, Money amount)
        throws BusinessException {
        // 1. Check withdrawal limit:
        if (amount.greaterThan(this.transferLimit)) {
            throw new BusinessException("Limit exceeded!");
        }
        // 2. Assuming result is 9-digit bank account number, validate 11-test:
        int sum = 0;
        for (int i = 0; i < counterAccount.length(); i++) {
            sum = sum + (9-i) * Character.getNumericValue(
                counterAccount.charAt(i));
        }
        if (sum % 11 == 0) {
            // 3. Look up counter account and make transfer object:
            CheckingAccount acct = Accounts.findAcctByNumber(counterAccount);
            Transfer result = new Transfer(this, acct, amount);
            return result;
        } else {
            throw new BusinessException("Invalid account number!");
        }
    }
}
