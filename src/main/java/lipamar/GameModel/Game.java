package lipamar.GameModel;

public class Game {
    private Board board;
    private Turn turn;
    private Referee referee;

    public Game() {
        newGame();
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

    public void nextGame(){
        turn.next();
        board = new Board();
        referee.nextRound(board);
    }
    public void newGame(){
        turn = new Turn();
        board = new Board();
        referee = new Referee(board);

    }
}
