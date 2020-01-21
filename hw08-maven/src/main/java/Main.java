
/*
 * Created by Koshkarov Vitaliy on 15.01.2020
 */

import com.google.gson.Gson;
import myjsonwriter.MyJsonWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------");

        BagOfPrimitives bag1 = new BagOfPrimitives(22, "тест", 10.3d, "13,4");
        Gson gson = new Gson();
        String json1 = gson.toJson(bag1);
        System.out.println(json1);
        BagOfPrimitives bag2 = gson.fromJson(json1, BagOfPrimitives.class);
        System.out.println(bag1.equals(bag2));
        System.out.println(bag2);

        System.out.println("---------------------------------------------");

        BagOfPrimitives myBag1 = new BagOfPrimitives(42, null, 0.0, "someValue");
        MyJsonWriter myJsonWriter = new MyJsonWriter();
        String myJson1 = myJsonWriter.toJson(myBag1);
        System.out.println(myJson1);
        BagOfPrimitives myBag2 = gson.fromJson(myJson1, BagOfPrimitives.class);
        System.out.println(myBag1.equals(myBag2));
        System.out.println(myBag2);

        System.out.println("---------------------------------------------");

        String obj1 = "abc\"def";
        MyJsonWriter jsonWriter = new MyJsonWriter();
        String json = jsonWriter.toJson(obj1);
        System.out.println(json);
        String obj2 = gson.fromJson(json, String.class);
        System.out.println(obj1.equals(obj2));
        System.out.println(obj2);
    }
}
