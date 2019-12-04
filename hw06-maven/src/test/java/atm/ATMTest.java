package atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.atm.ATM;
import ru.koshkarovvitaliy.Banknote;

public class ATMTest {
    private ATM atm;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of ATM and Banknote.$100 ")
    void init() {
        atm = new ATM();
    }

    @Test
    @DisplayName("Testing of the method \"balance\"")
    void testMethodBalance() {
        int expectedSum = 8_000;
        int sum = atm.balance();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("Sums are equal");
    }

    @Test
    @DisplayName("Testing of the method \"receiveBanknote\" with $100")
    void testMethodReceiveBanknote() {
        int expectedATMSum = 8_100;

        atm.receiveBanknote(Banknote.$100);

        int sumATM = atm.balance();

        Assertions.assertEquals(sumATM, expectedATMSum);
        System.out.println("Sums are equal");
    }
}
