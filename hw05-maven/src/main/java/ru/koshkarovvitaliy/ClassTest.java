package ru.koshkarovvitaliy;
/*
 * Created by Koshkarov Vitaliy on 21.01.2020
 */

import ru.koshkarovvitaliy.annotations.Before;
import ru.koshkarovvitaliy.annotations.Test;
import ru.koshkarovvitaliy.annotations.After;

public class ClassTest {

    public ClassTest() {}

    @After
    public void method1() {
        System.out.println("Method1");
    }

    @Test
    public void method2() {
        System.out.println("Method2");
    }

    @Before
    private void method3() {
        System.out.println("Method3");
    }

    @After
    private void method4() {
        System.out.println("Method4");
    }

    @Test
    private void method5() {
        System.out.println("Method5");
    }
}
