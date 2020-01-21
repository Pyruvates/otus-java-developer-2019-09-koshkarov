
/*
 * Created by Koshkarov Vitaliy on 20.01.2020
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import myjsonwriter.MyJsonWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyJsonWriterTest {
    private MyJsonWriter jsonWriter;
    private Gson gson;

    @Test
    @BeforeEach
    void initMyJsonWriter() {
        jsonWriter = new MyJsonWriter();
    }

    @Test
    @BeforeEach
    void initGson() {
        this.gson = new Gson();
    }

    @Test
    void compareHashMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Alan", "Turing");
        map.put("John", "von Neumann");
        map.put("James", "Gosling");

        String json = jsonWriter.toJson(map);
        System.out.println(json);

        Map myMap = gson.fromJson(json, Map.class);
        Assertions.assertEquals(map, myMap);
    }

    @Test
    void compareComplexObject() {
        String[] strings = {"Cool", "task", "Java", "the best!"};
        int[] ints = {-1, 0, 1};
        List<Integer> integerList = new ArrayList<>(Arrays.asList(42, Integer.MAX_VALUE));

        ComplexObject complexObject1 = new ComplexObject(strings, ints, integerList);
        String json = jsonWriter.toJson(complexObject1);
        System.out.println(json);
        ComplexObject complexObject2 = gson.fromJson(json, ComplexObject.class);
        Assertions.assertEquals(complexObject1, complexObject2);
    }

    @Test
    void escapeQuotes() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        assertEquals(gson.toJson("abc\"def"), jsonWriter.toJson("abc\"def"));
    }
}
