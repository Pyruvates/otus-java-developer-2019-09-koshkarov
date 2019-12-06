package ru.koshkarovvitaliy.atm;
/*
 * Created by Koshkarov Vitaliy on 06.12.2019
 */

import ru.koshkarovvitaliy.Banknote;

public interface ATM {
    void receiveBanknote(final Banknote banknote);
    void giveOutMinBanknotes(int sum);
    int balance();
}
