
/*
 * Created by Koshkarov Vitaliy on 20.01.2020
 */

import java.util.Objects;

public class BagOfPrimitives {
    private final int value1;
    private final String value2;
    private final double value3;
    private final String value4;

    public BagOfPrimitives(int value1, String value2, double value3, String value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    @Override
    public String toString() {
        return "BagOfPrimitives{" +
            "value1=" + value1 +
            ", value2='" + value2 + '\'' +
            ", value3=" + value3 +
            ", value4='" + value4 + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BagOfPrimitives that = (BagOfPrimitives) o;
        return value1 == that.value1 && Objects.equals(value2, that.value2)
            && value3 == that.value3 && Objects.equals(value4, that.value4);

    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3, value4);
    }
}
