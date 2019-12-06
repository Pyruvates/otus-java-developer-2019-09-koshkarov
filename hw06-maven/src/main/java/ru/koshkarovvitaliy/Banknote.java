package ru.koshkarovvitaliy;

public enum Banknote {
    $100(100),
    $200(200),
    $500(500);

    private final int faceValue;

    Banknote(int faceValue) {
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }
}
