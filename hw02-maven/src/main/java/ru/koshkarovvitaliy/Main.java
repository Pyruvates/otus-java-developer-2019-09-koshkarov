package ru.koshkarovvitaliy;

public class Main {
    public static void main(String[] args) {
        DIYarrayList<Integer> diyArray = new DIYarrayList<>();
        System.out.println(diyArray.size());
        diyArray.add(2);
        //diyArray.add(7);
        //diyArray.add(-3);
        System.out.println(diyArray.size());
        System.out.println(diyArray.isEmpty());
        for (Integer elem : diyArray) {
            System.out.println(elem);
        }
    }
}