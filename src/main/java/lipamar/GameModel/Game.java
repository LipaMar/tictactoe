package lipamar.GameModel;

public class Game {
    private Board board;
    private final Turn turn;
    private Referee referee;

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

    public void newGame(){
        referee = new Referee();
        board = new Board(referee);

    }
}
