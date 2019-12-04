package ru.koshkarovvitaliy.bankcells;

import ru.koshkarovvitaliy.Banknote;

public interface BankCell {
    int countSum();
    Banknote getName();
    void addBanknote();
    void giveOutBanknote();
}
