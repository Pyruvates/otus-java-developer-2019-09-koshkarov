package ru.koshkarovvitaliy.annotations;
/*
 * Created by Koshkarov Vitaliy on 21.01.2020
 */

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
