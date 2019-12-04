package ru.koshkarovvitaliy.bankcells;

import ru.koshkarovvitaliy.Banknote;

public class BankCellBuilder implements BankCell {
    private Banknote banknote;
    private int quantityOfBanknotes;

    public BankCellBuilder(Banknote banknote, int quantityOfBanknotes) {
        this.banknote = banknote;
        this.quantityOfBanknotes = quantityOfBanknotes;
    }

    @Override
    public int countSum() {
        int sum = 0;
        int faceValue = Integer.parseInt(this.banknote.name().substring(1));
        sum +=  faceValue * this.quantityOfBanknotes;
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
        this.quantityOfBanknotes -= 1;
    }
}
