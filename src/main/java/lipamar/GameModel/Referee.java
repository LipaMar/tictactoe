package lipamar.GameModel;

import lipamar.App;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Referee implements PropertyChangeListener {

    private UngroupedBoard<Field> ungroupedBoard;
    private Board board;
    private final Map<Mark, Integer> scores;
    private List<Field> winningLine;

    public Referee(Board board) {
        this.board = board;
        scores = new HashMap<>();
        winningLine = new ArrayList<>();
        scores.put(Mark.CROSS, 0);
        scores.put(Mark.NOUGHT, 0);
        this.board.setPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Board.BOARD) && evt.getNewValue() != null) {
            board = (Board) evt.getNewValue();
            ungroupedBoard = new UngroupedBoard<>(board.toList());
            winningLine = findWinner();

            if (isGameOver()) {
                addScore(result());
            } else {
                App.GAME.getTurn().next();
            }
        }

    }

    public boolean isGameOver() {
        return !winningLine.isEmpty() || board.isFull();
    }

    public Map<Mark, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
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

    public List<Field> getWinningLine() {
        return winningLine;
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

    public void nextRound(Board board) {
        this.board = board;
        this.board.setPropertyChangeListener(this);
        winningLine = new ArrayList<>();
    }
}
