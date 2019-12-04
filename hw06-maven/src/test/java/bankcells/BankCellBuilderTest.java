package bankcells;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.BankCell;
import ru.koshkarovvitaliy.bankcells.BankCellBuilder;

class BankCellBuilderTest {
    private BankCell bankCell;
    private int quantityOfBanknotes = 10;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of BankCellBuilder with $100 instance")
    void init() {
        bankCell = new BankCellBuilder(Banknote.$100, quantityOfBanknotes);
    }

    @Test
    @DisplayName("Testing method \'getName\' with $100 instance")
    void testMethodGetName() {
        Banknote banknote = bankCell.getName();
        Assertions.assertSame(banknote, Banknote.$100);
        System.out.println("Equality");
    }

    @Test
    @DisplayName("Testing method \'countSum\' with $100 instance")
    void testMethodCountSum() {
        int requiredSum = 1_000;
        int sum = bankCell.countSum();
        Assertions.assertEquals(requiredSum, sum);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
    }

    @Test
    @DisplayName("Testing method \'addBanknote\' with $100 instance")
    void testMethodAddBanknote() {
        int requiredSum = 1_100;
        bankCell.addBanknote();
        int sum = bankCell.countSum();
        Assertions.assertEquals(requiredSum, sum);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
    }
}
