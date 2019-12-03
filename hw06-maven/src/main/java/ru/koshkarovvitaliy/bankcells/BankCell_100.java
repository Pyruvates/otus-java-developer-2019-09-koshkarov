package ru.koshkarovvitaliy.bankcells;

import ru.koshkarovvitaliy.Banknote;

import java.util.Map;
import java.util.HashMap;

public class BankCell_100 implements BankCell {
    private final Banknote name = Banknote.$100;
    private final Map<Banknote, Integer> cell;

    @Override
    public int countSum() {
        int sum = 0;
        for (Map.Entry<Banknote, Integer> pair : cell.entrySet()) {
            String banknote = pair.getKey().toString().substring(1);
            Integer quantity = pair.getValue();
            sum = Integer.parseInt(banknote) * quantity;
        }
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

    public BankCell_100() {
        cell = new HashMap<>();
        cell.put(Banknote.$100, 10);
    }

    @Override
    public void addBanknote() {
        int oldValue = cell.get(name);
        int newValue = oldValue + 1;
        cell.replace(name, oldValue, newValue);
    }
}
