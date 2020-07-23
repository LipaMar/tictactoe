package lipamar.GameModel;

import java.util.Random;

public class Turn {
    private Mark mark;
    private boolean gameOver = false;

    public Turn() {
        Random random = new Random();
        mark = Mark.values()[random.nextInt(Mark.values().length)];
    }

    public Mark getMark() {
        return mark;
    }

    public void next() {
        mark = mark == Mark.NOUGHT ? Mark.CROSS : Mark.NOUGHT;
    }
}
