package myjsonwriter;
/*
 * Created by Koshkarov Vitaliy on 15.01.2020
 */

import java.util.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.StringEscapeUtils;

public class MyJsonWriter {

    public String toJson(Object obj) {
        String result = convertToString(obj);
        return result;
    }

    private String convertToString(Object obj) {
        if (obj == null) return null;

        List<String> fieldsOfStrings = new ArrayList<>();

        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields == null) return null;

        if (isJsonArray(obj) || isJsonValue(obj)) return getJsonElement(obj);

        for (Field field : fields) {
            try {
                field.setAccessible(true);

                int modifier = field.getModifiers();
                if (Modifier.isTransient(modifier) || Modifier.isStatic(modifier)) continue;

                Object value = field.get(obj);
                if (value == null) ElementWrapper.QUOTES.wrap(null);

                fieldsOfStrings.add(ElementWrapper.QUOTES.wrap(field.getName()) + ":" + getJsonElement(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return ElementWrapper.CURLY_BRACKETS.wrap(String.join(",", fieldsOfStrings));
    }

    private String getJsonElement(Object obj) {
        if (obj == null) return null;

        if (isPrimitiveType(obj)) return obj.toString();
        if (obj instanceof Character) return ElementWrapper.QUOTES.wrap(obj.toString());
        if (obj instanceof String) return ElementWrapper.QUOTES.wrap(StringEscapeUtils.escapeJava((String) obj));

        if (isArray(obj)) return getArray(obj);
        if (isIterable(obj)) return getIterable(obj);
        if (isMap(obj)) return getMap(obj);

        return convertToString(obj);
    }

    private boolean isJsonArray(Object obj) {
        return isArray(obj)
            || isIterable(obj)
            || isMap(obj);
    }

    private boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    private boolean isIterable(Object obj) {
        return obj instanceof Iterable;
    }

    private boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    private boolean isJsonValue(Object obj) {
        return Objects.isNull(obj)
            || isPrimitiveType(obj)
            || obj instanceof String
            || obj instanceof Character;
    }

    private boolean isPrimitiveType(Object obj) {
        return obj instanceof Boolean
            || obj  instanceof Integer
            || obj instanceof Byte
            || obj instanceof Short
            || obj instanceof Long
            || obj instanceof Float
            || obj instanceof Double;
    }

    private String getArray(Object obj) {
        List<String> elements = new ArrayList<>();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object arrayElement = Array.get(obj, i);
            elements.add(getJsonElement(arrayElement));
        }
        return ElementWrapper.SQUARE_BRACKETS.wrap(String.join(",", elements));
    }

    private String getIterable(Object obj) {
        if (obj == null) return ElementWrapper.SQUARE_BRACKETS.wrap("");
        if (obj instanceof List) return getArray( ((List) obj).toArray() );
        if (obj instanceof Set)  return getArray( ((Set) obj).toArray() );
        return null;
    }

    private String getMap(Object obj) {
        Map<Object, Object> map = (Map) obj;
        if (map == null) return ElementWrapper.CURLY_BRACKETS.wrap("");

        List<String> elements = new ArrayList<>();
        for (Map.Entry<Object, Object> pair : map.entrySet()) {
            elements.add(ElementWrapper.QUOTES.wrap(pair.getKey().toString()) + ":" + getJsonElement(pair.getValue()));
        }
        return ElementWrapper.CURLY_BRACKETS.wrap(String.join(",", elements));
    }
}
