package bankcells;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.BankCell;
import ru.koshkarovvitaliy.bankcells.BankCellImpl;

public class BankCellImplTest {
    private BankCell bankCell;
    private int quantityOfBanknotes = 10;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of BankCellImpl with $100")
    void init() {
        bankCell = new BankCellImpl(Banknote.$100, quantityOfBanknotes);
    }

    @Test
    @DisplayName("Testing method \'getName()\' with $100 instance")
    void testMethodGetName() {
        Banknote banknote = bankCell.getName();
        Assertions.assertSame(banknote, Banknote.$100);
        System.out.println("The name of the cell is \'" + banknote + "\'");
    }

    @Test
    @DisplayName("Testing method \'countSum()\' with $100 instance")
    void testMethodCountSum() {
        int expectedSum = bankCell.faceValue() * quantityOfBanknotes;
        int sum = bankCell.countSum();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("The sum in the cell " + bankCell.getName() + ": " + sum);
    }

    @Test
    @DisplayName("Testing method \'addBanknote()\' with $100 instance")
    void testMethodAddBanknote() {
        int expectedSum = bankCell.faceValue() * (quantityOfBanknotes + 1);
        bankCell.addBanknote();
        int sum = bankCell.countSum();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("The sum in the cell after adding " + bankCell.getName() + ": " + sum);
    }

    @Test
    @DisplayName("Testing method \'getFaceValue()\'")
    void testMethodGetFaceValue() {
        int expectedFaceValue = 100;
        int faceValue = bankCell.faceValue();
        Assertions.assertEquals(expectedFaceValue, faceValue);
        System.out.println("FaceValue is " + faceValue);
    }

    @Test
    @DisplayName("Testing method \'giveOutBanknote()\' after once give out")
    void testMethodGiveOutBanknoteOnce() {
        bankCell.giveOutBanknote();

        int expectedSum = bankCell.faceValue() * (quantityOfBanknotes - 1);
        int sum = bankCell.countSum();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("The sum in the cell after once give out " + bankCell.getName() + ": " + sum);
    }

    @Test
    @DisplayName("Testing method \'giveOutBanknote()\' after full give out")
    void testMethodGiveOutBanknoteFull() {
        for (int i = 0; i < quantityOfBanknotes + 1; i++) {
            bankCell.giveOutBanknote();
        }

        int expectedSum = 0;
        int sum = bankCell.countSum();
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("The sum in the cell after full give out " + bankCell.getName() + ": " + sum);
    }
}
