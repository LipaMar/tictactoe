package lipamar;

public enum Mark {
    NOUGHT("o"),
    CROSS("x");
    private String sign;

    Mark(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
