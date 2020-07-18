package lipamar;

public class Turn {
    private Mark nextMark;
    public Turn(){
        System.out.println(Enum.valueOf(Mark.class,"Cross"));
    }

    public Mark getNextMark() {
        return nextMark;
    }
}
