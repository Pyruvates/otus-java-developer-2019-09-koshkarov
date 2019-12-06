package ru.koshkarovvitaliy;

import ru.koshkarovvitaliy.atm.ATMImpl;

public class Main {
    public static void main(String[] args) {
        ATMImpl atm = new ATMImpl();
        int balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance + "\r\n");

        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$200);
        atm.receiveBanknote(Banknote.$200);
        atm.receiveBanknote(Banknote.$500);
        atm.receiveBanknote(Banknote.$500);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance + "\r\n");

        atm.giveOutMinBanknotes(8300);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance + "\r\n");

        atm.giveOutMinBanknotes(1700);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance + "\r\n");

        atm.giveOutMinBanknotes(42);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance + "\r\n");

        atm.giveOutMinBanknotes(1400);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);
    }
}
