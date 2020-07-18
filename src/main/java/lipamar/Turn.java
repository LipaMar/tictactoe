package lipamar;

import java.util.Random;

public class Turn {
    private Mark nextMark;
    public Turn(){
        Random random = new Random();
        nextMark = Mark.values()[random.nextInt(Mark.values().length)];
        System.out.println("Player with "+nextMark+" will start!");
    }

    public Mark getNextMark() {
        return nextMark;
    }
    public void next(){
        nextMark = nextMark==Mark.NOUGHT?Mark.CROSS:Mark.NOUGHT;
    }
}
