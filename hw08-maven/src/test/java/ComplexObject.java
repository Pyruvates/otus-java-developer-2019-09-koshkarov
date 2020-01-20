
/*
 * Created by Koshkarov Vitaliy on 20.01.2020
 */

import java.util.List;
import java.util.Arrays;
import java.util.Objects;

public class ComplexObject {
    private String[] value1;
    private int[] value2;
    private List<Integer> value3;

    ComplexObject(String[] value1, int[] value2, List<Integer> value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexObject that = (ComplexObject) o;
        return Arrays.equals(value1, that.value1) && Arrays.equals(value2, that.value2) && value3.equals(that.value3);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value3);
        result = 31 * result + Arrays.hashCode(value1);
        result = 31 * result + Arrays.hashCode(value2);
        return result;
    }
}
