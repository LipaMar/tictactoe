package lipamar;

public class Field {
    private final int X;
    private final int Y;
    private Mark mark;

    public Field(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        if(this.mark==null)
            this.mark = mark;
    }
}
