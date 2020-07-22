package lipamar.GameModel;

public class Game {
    private final Board board;
    private final Turn turn;
    private final Referee referee;

    public Game() {
        referee = new Referee();
        turn = new Turn();
        board = new Board(referee);
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }

    public Referee getReferee() {
        return referee;
    }

    public void makeAMove(int row, int column) {
        board.setMarkOnField(turn.getMark(), row, column);
    }
}
