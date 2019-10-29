package ru.koshkarovvitaliy;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        List<Integer> dest = new DIYarrayList<>();
        for (int i = 0; i < 30; i++) {
            dest.add(new Random().nextInt(10));
        }
        for (Integer elem : dest) {
            System.out.print(elem + " ");
        }
        System.out.println();

        Collections.sort(dest);

        for (Integer elem : dest) {
            System.out.print(elem + " ");
        }
        System.out.println();

        List<Object> addAll = new ArrayList<>();

        Collections.addAll(addAll, dest.toArray());

        for (Object elem : addAll) {
            System.out.print(elem + " ");
        }
        System.out.println();

        List<Integer> src = new ArrayList<>();
        src.add(99);
        src.add(98);
        src.add(97);

        Collections.copy(dest, src);

        for (Integer elem : dest) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }
}