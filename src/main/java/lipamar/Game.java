package lipamar;

public class Game {
    private Board board;
    public Game() {
        board = new Board();
        Turn turn = new Turn();
        Player player1 = new Player(Mark.CROSS,turn);
        Player player2 = new Player(Mark.NOUGHT,turn);
        new Thread(player1).start();
        new Thread(player2).start();
    }
}
