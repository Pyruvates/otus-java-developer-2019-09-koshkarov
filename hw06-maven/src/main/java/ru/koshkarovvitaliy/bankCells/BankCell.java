package ru.koshkarovvitaliy.bankCells;

import ru.koshkarovvitaliy.Banknote;

import java.util.Map;

public interface BankCell {
    int countSum();
    Map<Banknote, Integer> getCell();
    Banknote getName();
}
