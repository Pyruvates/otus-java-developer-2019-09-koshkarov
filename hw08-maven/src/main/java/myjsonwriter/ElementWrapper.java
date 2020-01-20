package myjsonwriter;
/*
 * Created by Koshkarov Vitaliy on 17.01.2020
 */

public enum ElementWrapper {
    SQUARE_BRACKETS {
        @Override
        public String wrap(String value) {
            return "[" + value + "]";
        }
    },

    CURLY_BRACKETS {
        @Override
        public String wrap(String value) {
            return "{" + value + "}";
        }
    },

    QUOTES {
        @Override
        public String wrap(String value) {
            return "\"" + value + "\"";
        }
    };

    public abstract String wrap(String value);
}
