package ru.koshkarovvitaliy.bankcells;

import ru.koshkarovvitaliy.Banknote;

public interface BankCell {
    int countSum();
    Banknote getName();
    int faceValue();
    void addBanknote();
    void giveOutBanknote();
}
