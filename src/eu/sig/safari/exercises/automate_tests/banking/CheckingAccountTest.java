package eu.sig.safari.exercises.automate_tests.banking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import eu.sig.safari.exercises.automate_tests.banking.stubs.BusinessException;

public class CheckingAccountTest {

    /*
     * Bank account number 123456789 is valid
     *   (because (1*9)+(2*8)+(3*7)+(4*6)+(5*5)+(6*4)+(7*3)+(8*2)+(9*1)=165, which can be
     *   divided by 11 (as 165=15*11).
     *   
     * Given that 123456789 is valid, 123456788 is not valid (because only one digit,
     *   the last one, is different).
     */

    CheckingAccount acct = new CheckingAccount();

    /*---- Below are some templates you can use for writing your unit tests ----*/
    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test(expected = BusinessException.class)
    public void testExpectedException() {
        fail("Not yet implemented");

    }

    @Test
    public void differentWayToTestExpectedException() {
        final String expectedMessage = "Limit exceeded!";
        try {

            // Call you method here

        } catch (BusinessException e) {
            assertEquals("BusinessException's message should be \"" + expectedMessage + "\"", expectedMessage,
                e.getMessage());
        }
    }

    /*--- Please write your unit tests here ---- */

}
