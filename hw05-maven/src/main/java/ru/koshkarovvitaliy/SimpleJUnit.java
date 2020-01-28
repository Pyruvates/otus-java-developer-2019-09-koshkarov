package ru.koshkarovvitaliy;
/*
 * Created by Koshkarov Vitaliy on 21.01.2020
 */

import ru.koshkarovvitaliy.annotations.After;
import ru.koshkarovvitaliy.annotations.Test;
import ru.koshkarovvitaliy.annotations.Before;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class SimpleJUnit {

    public static void start(String className) throws ClassNotFoundException {
        Class<?> testClass = Class.forName(className);

        List<Method> methodsBefore = new ArrayList<>();
        List<Method> methodsTest = new ArrayList<>();
        List<Method> methodsAfter = new ArrayList<>();
        int failedTests = 0;
        int passedTests = 0;

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class))
                methodsBefore.add(method);

            if (method.isAnnotationPresent(Test.class))
                methodsTest.add(method);

            if (method.isAnnotationPresent(After.class))
                methodsAfter.add(method);
        }

        for (Method testMethod : methodsTest) {
            System.out.println("Testing method: " + testMethod.getName());

            try {
                Object obj = testClass.getDeclaredConstructor().newInstance();

                System.out.println("Calls methods @Before:");

                for (Method method : methodsBefore) {
                    method.setAccessible(true);
                    method.invoke(obj);
                }

                System.out.println("Calls method @Test:");

                testMethod.setAccessible(true);
                testMethod.invoke(obj);

                System.out.println("Calls methods @After:");

                for (Method method : methodsAfter) {
                    method.setAccessible(true);
                    method.invoke(obj);
//                    for testing failed method calls:
                    try {
                        throw new IllegalAccessException("Exception has been caught when call method: " + method.getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        failedTests++;
                    }
                }

                System.out.println("----------");
                passedTests++;
            } catch (Exception e) {
                System.out.println("Test failed. " + e.getMessage());
                System.out.println();
                failedTests++;
            }
        }

        System.out.println("Tests passed: " + passedTests);
        System.out.println("Tests failed: " + failedTests);
        System.out.println("Total tests: " + (passedTests + failedTests));
    }
}
