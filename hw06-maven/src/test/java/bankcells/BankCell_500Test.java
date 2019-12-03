package bankcells;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.BankCell;
import ru.koshkarovvitaliy.bankcells.BankCell_500;

import java.util.Map;
import java.util.HashMap;

public class BankCell_500Test {
    private BankCell bankCell;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of BankCell_500")
    void init() {
        bankCell = new BankCell_500();
    }

    @Test
    @DisplayName("Testing method \'getName\'")
    void testMethodGetName() {
        Banknote banknote = bankCell.getName();
        Assertions.assertSame(banknote, Banknote.$500);
        System.out.println("Equality");
    }

    @Test
    @DisplayName("Testing method \'getCell\'")
    void testMethodGetCell() {
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(Banknote.$500, 10);
        Map<Banknote, Integer> mapCell = bankCell.getCell();
        Assertions.assertEquals(map, mapCell);
        System.out.println("Map is ok");
    }

    @Test
    @DisplayName("Testing method \'countSum\'")
    void testMethodCountSum() {
        int expectedSum = 5_000;
        int sum = bankCell.countSum();
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
        Assertions.assertEquals(expectedSum, sum);
        System.out.println("Sums are equal");
    }

    @Test
    @DisplayName("Testing method \'addBanknote\'")
    void testMethodAddBanknote() {
        Map<Banknote, Integer> mapCell = bankCell.getCell();
        int numberOfBanknotes = mapCell.get(Banknote.$500);
        int sum = bankCell.countSum();

        Assertions.assertEquals(numberOfBanknotes, 10);
        System.out.println("The number of banknotes in the cell is " + numberOfBanknotes);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);

        bankCell.addBanknote();

        numberOfBanknotes = bankCell.getCell().get(Banknote.$500);
        sum = bankCell.countSum();

        Assertions.assertEquals(numberOfBanknotes, 11);
        System.out.println("The number of banknotes in the cell is " + numberOfBanknotes);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
    }
}
