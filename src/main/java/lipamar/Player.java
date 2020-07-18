package lipamar;

public class Player implements Runnable {
    private final Turn turnLock;
    private Mark mark;

    public Player(Mark mark, Turn turn) {
        this.turnLock = turn;
        this.mark = mark;
    }

    @Override
    public void run() {

        while (true) {
            if (turnLock.getNextMark() == mark) {
                synchronized (turnLock) {
                    try {
                        System.out.println(mark);
                        turnLock.next();
                        turnLock.notifyAll();
                        turnLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
