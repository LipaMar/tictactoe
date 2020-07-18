package lipamar;

public class Player implements Runnable {
    private Turn turnLock;
    public Player(Turn turn) {
        turnLock = turn;
    }

    @Override
    public void run() {


    }
}
