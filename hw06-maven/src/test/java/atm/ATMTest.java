package atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.atm.ATM;
import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.BankCell;
import ru.koshkarovvitaliy.bankcells.BankCell_100;
import ru.koshkarovvitaliy.bankcells.BankCell_200;
import ru.koshkarovvitaliy.bankcells.BankCell_500;

public class ATMTest {
    private ATM atm;
    private BankCell bankCell;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of ATM")
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
    void testMethodReceiveBanknote100() {
        int expectedATMSum = 8_100;
        int expectedCellSum = 1_100;
        int expectedNumberOfBanknotes = 11;

        bankCell = new BankCell_100();
        bankCell.addBanknote();

        atm.receiveBanknote(Banknote.$100);

        int sumATM = atm.balance();
        int sumCell = bankCell.countSum();
        int numberOfBanknotes = bankCell.getCell().get(Banknote.$100);

        Assertions.assertEquals(sumATM, expectedATMSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(sumCell, expectedCellSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(numberOfBanknotes, expectedNumberOfBanknotes);
        System.out.println("The number of banknotes is equal");
    }

    @Test
    @DisplayName("Testing of the method \"receiveBanknote\" with $200")
    void testMethodReceiveBanknote200() {
        int expectedATMSum = 8_200;
        int expectedCellSum = 2_200;
        int expectedNumberOfBanknotes = 11;

        bankCell = new BankCell_200();
        bankCell.addBanknote();

        atm.receiveBanknote(Banknote.$200);

        int sumATM = atm.balance();
        int sumCell = bankCell.countSum();
        int numberOfBanknotes = bankCell.getCell().get(Banknote.$200);

        Assertions.assertEquals(sumATM, expectedATMSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(sumCell, expectedCellSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(numberOfBanknotes, expectedNumberOfBanknotes);
        System.out.println("The number of banknotes is equal");
    }

    @Test
    @DisplayName("Testing of the method \"receiveBanknote\" with $500")
    void testMethodReceiveBanknote500() {
        int expectedATMSum = 8_500;
        int expectedCellSum = 5_500;
        int expectedNumberOfBanknotes = 11;

        bankCell = new BankCell_500();
        bankCell.addBanknote();

        atm.receiveBanknote(Banknote.$500);

        int sumATM = atm.balance();
        int sumCell = bankCell.countSum();
        int numberOfBanknotes = bankCell.getCell().get(Banknote.$500);

        Assertions.assertEquals(sumATM, expectedATMSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(sumCell, expectedCellSum);
        System.out.println("Sums are equal");

        Assertions.assertEquals(numberOfBanknotes, expectedNumberOfBanknotes);
        System.out.println("The number of banknotes is equal");
    }
}
