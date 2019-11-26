package ru.koshkarovvitaliy.atm;

import ru.koshkarovvitaliy.Banknote;
import ru.koshkarovvitaliy.bankCells.BankCell;
import ru.koshkarovvitaliy.bankCells.BankCell_100;
import ru.koshkarovvitaliy.bankCells.BankCell_200;

import java.util.List;
import java.util.ArrayList;

public class ATM {
    private final List<BankCell> cells = new ArrayList<>();

    public ATM() {
        this.cells.add(BankCell_100.getInstance());
        this.cells.add(BankCell_200.getInstance());
    }

    public void receiveBanknote(final Banknote banknote) {
        addBanknoteToCell(banknote);
    }

    public void giveOutMinBanknotes(final int sum) {
        if (sum % 100 == 0) {
            if (isEnoughBalance(sum)) {
                giveOut(sum);
            } else {
                System.out.println("Выдать запрашиваемую сумму $" + sum + " невозможно. Недостаточно средств.");
            }
        } else {
            System.out.println("Введите сумму кратную 100.");
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
                final Banknote key = elem.getName();
                final int oldValue = elem.getCell().get(key);
                final int newValue = oldValue + 1;
                elem.getCell().replace(key, oldValue, newValue);
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

    private void giveOut(final int requiredSum) {
        final BankCell cell_100 = cells.get(0);
        final BankCell cell_200 = cells.get(1);
        final int firstHalf;
        final int secondHalf;

        if (requiredSum == 200) {
            final int howMuch = 2;
            final int oldValue = cell_100.getCell().get(cell_100.getName());
            final int newValue = oldValue - howMuch;
            cell_100.getCell().replace(cell_100.getName(), oldValue, newValue);
        } else if (requiredSum % 200 == 0) {
            firstHalf = requiredSum / 2;
            secondHalf = requiredSum - firstHalf;

            giveOutHalfSum(firstHalf, cell_100);
            giveOutHalfSum(secondHalf, cell_200);
        } else {
            firstHalf = (requiredSum / 2) - 50;
            secondHalf = (requiredSum / 2) + 100;

            giveOutHalfSum(firstHalf, cell_100);
            giveOutHalfSum(secondHalf, cell_200);
        }
        System.out.println("Выдана запрашиваемая сумма: $" + requiredSum);
    }

    private void giveOutHalfSum(final int half, final BankCell bankCell) {
        final int faceValue = Integer.parseInt(bankCell.getName().toString().substring(1));
        final int howMuch = half / faceValue;
        final int oldValue = bankCell.getCell().get(bankCell.getName());
        final int newValue = oldValue - howMuch;
        bankCell.getCell().replace(bankCell.getName(), oldValue, newValue);
    }
}
