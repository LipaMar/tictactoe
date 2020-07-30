package lipamar.GameModel;

import java.util.List;
import java.util.Map;

public class Game {
    private Board board;
    private Referee referee;

    public Game() {
        newGame();
    }

    public List<List<Field>> getBoard() {
        return board.toList();
    }

    public Mark whoseTurn() {
        return referee.whoseTurn();
    }


    public void makeAMove(int row, int column) {
        board.setMarkOnField(referee.whoseTurn(),row, column);
    }

    public void nextGame(){
        board = new Board();
        referee.nextRound(board);
    }
    public void newGame(){
        board = new Board();
        referee = new Referee(board);
    }
    public boolean isGameOver(){
        return referee.isGameOver();
    }
    public Map<Mark, Integer> getScores(){
        return referee.getScores();
    }
    public List<Field> getWinningLine(){
        return referee.getWinningLine();
    }
}
