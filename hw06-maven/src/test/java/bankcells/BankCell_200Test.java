package bankcells;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.BankCell;
import ru.koshkarovvitaliy.bankcells.BankCell_200;

import java.util.Map;
import java.util.HashMap;

public class BankCell_200Test {
    private BankCell bankCell;

    @Test
    @BeforeEach
    @DisplayName("Init new instance of BankCell_200")
    void init() {
        bankCell = new BankCell_200();
    }

    @Test
    @DisplayName("Testing method \'getName\'")
    void testMethodGetName() {
        Banknote banknote = bankCell.getName();
        Assertions.assertSame(banknote, Banknote.$200);
        System.out.println("Equality");
    }

    @Test
    @DisplayName("Testing method \'getCell\'")
    void testMethodGetCell() {
        Map<Banknote, Integer> map = new HashMap<>();
        map.put(Banknote.$200, 10);
        Map<Banknote, Integer> mapCell = bankCell.getCell();
        Assertions.assertEquals(map, mapCell);
        System.out.println("Map is ok");
    }

    @Test
    @DisplayName("Testing method \'countSum\'")
    void testMethodCountSum() {
        int expectedSum = 2_000;
        int sum = bankCell.countSum();
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
        Assertions.assertEquals(sum, expectedSum);
        System.out.println("Sums are equal");
    }

    @Test
    @DisplayName("Testing method \'addBanknote\'")
    void testMethodAddBanknote() {
        Map<Banknote, Integer> mapCell = bankCell.getCell();
        int numberOfBanknotes = mapCell.get(Banknote.$200);
        int sum = bankCell.countSum();

        Assertions.assertEquals(numberOfBanknotes, 10);
        System.out.println("The number of banknotes in the cell is " + numberOfBanknotes);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);

        bankCell.addBanknote();

        numberOfBanknotes = bankCell.getCell().get(Banknote.$200);
        sum = bankCell.countSum();

        Assertions.assertEquals(numberOfBanknotes, 11);
        System.out.println("The number of banknotes in the cell is " + numberOfBanknotes);
        System.out.println("Сумма ячейки " + bankCell.getName() + ": " + sum);
    }
}
