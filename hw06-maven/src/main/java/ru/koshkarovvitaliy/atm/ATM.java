package ru.koshkarovvitaliy.atm;

import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankcells.*;

import java.util.List;
import java.util.ArrayList;

public class ATM {
    private final List<BankCell> cells = new ArrayList<>();

    public ATM() {
        this.cells.add(new BankCellBuilder(Banknote.$100, 10));
        this.cells.add(new BankCellBuilder(Banknote.$200, 10));
        this.cells.add(new BankCellBuilder(Banknote.$500, 10));
    }

    public void receiveBanknote(final Banknote banknote) {
        addBanknoteToCell(banknote);
    }

    public void giveOutMinBanknotes(int sum) {
        int requiredSum = sum;
        if (isEnoughBalance(sum)) {
            if (sum % 100 == 0) {
                sum = giveBanknote(sum, Banknote.$500);
                sum = giveBanknote(sum, Banknote.$200);
                sum = giveBanknote(sum, Banknote.$100);
                System.out.println("Выдана запрашиваемая сумма: $" + requiredSum);
            } else {
                System.out.println("Невозможно выдать сумму. Введите сумму кратную 100");
            }
        } else {
            System.out.println("Невозможно выдать сумму: в банкомате недостаточно средств.");
        }
    }

    public int balance() {
        int balance = 0;
        for (BankCell elem : cells) {
            balance += elem.countSum();
        }
        return balance;
    }

    private void addBanknoteToCell(final Banknote banknote) {
        for (BankCell elem : cells) {
            if (elem.getName().equals(banknote)) {
                elem.addBanknote();
                System.out.println("Добавлена банкнота номиналом: " + elem.getName());
            }
        }
    }

    private boolean isEnoughBalance(final int sum) {
        if (sum <= balance()) {
            return true;
        }
        return false;
    }

    private int giveBanknote(int requiredSum, Banknote banknote) {
        int sum = requiredSum;
        int index = 0;
        BankCell bankCell = null;
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getName().equals(banknote)) {
                bankCell = cells.get(i);
                index = i;
                break;
            }
        }

        int faceValue = Integer.parseInt(cells.get(index).getName().toString().substring(1));
        while (sum - faceValue >= 0) {
            if (checkBalanceInCell(bankCell)) {
                bankCell.giveOutBanknote();
                sum = sum - faceValue;
            } else {
                break;
            }
        }
        return sum;
    }

    private boolean checkBalanceInCell(BankCell bankCell) {
        if (bankCell.countSum() > 0) {
            return true;
        }
        return false;
    }
}
