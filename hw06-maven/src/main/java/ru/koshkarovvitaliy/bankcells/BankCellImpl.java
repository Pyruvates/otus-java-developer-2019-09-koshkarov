package ru.koshkarovvitaliy.bankcells;

import ru.koshkarovvitaliy.Banknote;

public class BankCellImpl implements BankCell {
    private final Banknote banknote;
    private final int faceValue;
    private int quantityOfBanknotes;

    public BankCellImpl(Banknote banknote, int quantityOfBanknotes) {
        this.banknote = banknote;
        this.quantityOfBanknotes = quantityOfBanknotes;
        this.faceValue = banknote.getFaceValue();
    }

    @Override
    public int countSum() {
        int sum = 0;
        sum += banknote.getFaceValue() * this.quantityOfBanknotes;
        return sum;
    }

    @Override
    public Banknote getName() {
        return this.banknote;
    }

    @Override
    public void addBanknote() {
        this.quantityOfBanknotes += 1;
    }

    @Override
    public void giveOutBanknote() {
        if (isEnoughBanknotes()) {
            this.quantityOfBanknotes -= 1;
        }
    }

    @Override
    public int faceValue() {
        return faceValue;
    }

    private boolean isEnoughBanknotes() {
        return quantityOfBanknotes > 0;
    }
}
