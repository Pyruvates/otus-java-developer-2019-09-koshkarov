package ru.koshkarovvitaliy.bankCells;

import ru.koshkarovvitaliy.Banknote;

import java.util.Map;
import java.util.HashMap;

public class BankCell_100 implements BankCell {
    private final Banknote name = Banknote.$100;
    private static final BankCell bankCell = new BankCell_100();
    private final Map<Banknote, Integer> cell;

    public static BankCell getInstance() {
        synchronized (BankCell_100.class) {
            if (bankCell == null) {
                return new BankCell_100();
            }
            return bankCell;
        }
    }

    @Override
    public int countSum() {
        int sum = 0;
        for (Map.Entry<Banknote, Integer> pair : cell.entrySet()) {
            String banknote = pair.getKey().toString().substring(1);
            Integer quantity = pair.getValue();
            sum = Integer.parseInt(banknote) * quantity;
        }
        System.out.println("Сумма ячейки " + getName() + ": " + sum);
        return sum;
    }

    @Override
    public Map<Banknote, Integer> getCell() {
        return cell;
    }

    @Override
    public Banknote getName() {
        return name;
    }

    private BankCell_100() {
        cell = new HashMap<>();
        cell.put(Banknote.$100, 10);
    }
}
