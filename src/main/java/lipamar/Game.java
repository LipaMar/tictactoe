package lipamar;

public class Game {
    private final Board board;
    private final Turn turn;

    public Game() {
        board = new Board();
        turn = new Turn();
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }

    public void makeAMove(int row, int column) {
        Field field = board.getField(row, column);
        if (field.getMark() == null) {
            field.setMark(turn.getMark());
            turn.next();
        }
    }
}
