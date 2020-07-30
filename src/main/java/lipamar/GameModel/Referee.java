package lipamar.GameModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

class Referee implements PropertyChangeListener {

    private final Map<Mark, Integer> scores;
    private final Turn turn;
    private UngroupedBoard<Field> ungroupedBoard;
    private Board board;
    private List<Field> winningLine;

    public Referee(Board board) {
        this.board = board;
        this.board.registerPropertyChangeListener(this);
        turn = new Turn();
        scores = new HashMap<>();
        winningLine = new ArrayList<>();
        scores.put(Mark.CROSS, 0);
        scores.put(Mark.NOUGHT, 0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (checkEventData(evt)) {
            board = (Board) evt.getNewValue();
            ungroupedBoard = new UngroupedBoard<>(board.toList());
            winningLine = findWinner();

            if (isGameOver()) {
                addScore(result());
            }
            turn.next();
        }

    }

    public void nextRound(Board board) {
        this.board = board;
        this.board.registerPropertyChangeListener(this);
        winningLine = new ArrayList<>();
    }

    private boolean checkEventData(PropertyChangeEvent evt) {
        return evt.getPropertyName().equals(Board.BOARD) && evt.getNewValue() != null;
    }

    private Mark result() {
        return winningLine.isEmpty() ? null : winningLine.get(0).getMark();
    }

    private void addScore(Mark result) {
        if (result == null)
            draw();
        else
            win(result);
    }

    private void draw() {
        win(Mark.CROSS);
        win(Mark.NOUGHT);
    }

    private void win(Mark result) {
        int old = scores.get(result);
        scores.put(result, old + 1);
    }

    private boolean isCombinationWinning(List<Field> list) {
        return !containsFieldWithNoMark(list) && areNonNullObjectsEqual(list);
    }

    private List<Field> findWinner() {
        List<List<List<Field>>> groups = Arrays.asList(ungroupedBoard.getColumns(), ungroupedBoard.getRows(), ungroupedBoard.getDiagonals());
        Optional<List<Field>> optional = groups.stream().flatMap(Collection::stream).filter(this::isCombinationWinning).findFirst();
        return optional.orElseGet(ArrayList::new);
    }

    private boolean containsFieldWithNoMark(List<Field> list) {
        return list.stream().map(Field::getMark).anyMatch(Objects::isNull);
    }

    private boolean areNonNullObjectsEqual(List<Field> list) {
        return list.stream().map(Field::getMark).filter(Objects::nonNull).distinct().count() == 1;
}

    public Mark whoseTurn() {
        return turn.getMark();
    }

    public boolean isGameOver() {
        return !winningLine.isEmpty() || board.isFull();
    }

    public Map<Mark, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    public List<Field> getWinningLine() {
        return winningLine;
    }
}
