package ru.koshkarovvitaliy;
/*
 * Created by Koshkarov Vitaliy on 21.01.2020
 */

public class Main {
    public static void main(String[] args) {
        String className = ClassTest.class.getName();
        try {
            SimpleJUnit.start(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
