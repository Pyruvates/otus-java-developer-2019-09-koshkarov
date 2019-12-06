package atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.atm.ATMImpl;
import ru.koshkarovvitaliy.Banknote;

public class ATMImplTest {
    private ATMImpl atm;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of ATM")
    void init() {
        atm = new ATMImpl();
    }

    @Test
    @DisplayName("Testing of the method \"balance()\"")
    void testMethodBalance() {
        int expectedSum = 8_000;
        int sum = atm.balance();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("The sum in the ATM upon initialisation: " + sum);
    }

    @Test
    @DisplayName("Testing of the method \"receiveBanknote(final Banknote banknote)\" with $100")
    void testMethodReceiveBanknote() {
        int expectedATMSum = 8_100;

        atm.receiveBanknote(Banknote.$100);

        int sumATM = atm.balance();

        Assertions.assertEquals(sumATM, expectedATMSum);
        System.out.println("The sums in the ATM after once receive banknote: " + sumATM);
    }

    @Test
    @DisplayName("Testing of the method \"giveOutMinBanknotes(int sum)\" with $100")
    void testMethodGiveOutMinBanknotes() {
        int requiredSum = 3_700;
        atm.giveOutMinBanknotes(requiredSum);
        int expectedSum = 4_300;
        int sumATM = atm.balance();
        Assertions.assertEquals(expectedSum, sumATM);
        System.out.println("The sum in the ATM after give out " + requiredSum +": " + sumATM);
    }
}
