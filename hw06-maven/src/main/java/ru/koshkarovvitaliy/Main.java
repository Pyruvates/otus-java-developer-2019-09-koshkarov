package ru.koshkarovvitaliy;

import ru.koshkarovvitaliy.atm.ATM;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        int balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);

        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$100);
        atm.receiveBanknote(Banknote.$200);
        atm.receiveBanknote(Banknote.$200);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);

        atm.giveOutMinBanknotes(1500);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);

        atm.giveOutMinBanknotes(2000);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);

        atm.giveOutMinBanknotes(800);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);

        atm.giveOutMinBanknotes(1);

        balance = atm.balance();
        System.out.println("Общий баланс ATM: " + balance);
    }
}
