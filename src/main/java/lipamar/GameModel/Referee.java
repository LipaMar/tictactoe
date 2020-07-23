package lipamar.GameModel;

import lipamar.App;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Referee implements PropertyChangeListener {

    private UngroupedBoard<Field> board;
    private boolean gameOver = false;

    private List<Field> winningLine = new ArrayList<>();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Board.BOARD) && evt.getNewValue() != null) {
            board = new UngroupedBoard<>((List<List<Field>>) evt.getNewValue());
            winningLine = findWinner();
            if (!winningLine.isEmpty()) {
                gameOver = true;
            }
            if (!gameOver) App.GAME.getTurn().next();
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<Field> getWinningLine() {
        return winningLine;
    }

    private boolean isCombinationWinning(List<Field> list) {
        return !containsFieldWithNoMark(list) && areNonNullObjectsEqual(list);
    }

    private List<Field> findWinner() {
        List<List<List<Field>>> groups = Arrays.asList(board.getColumns(), board.getRows(), board.getDiagonals());
        Optional<List<Field>> optional = groups.stream().flatMap(Collection::stream).filter(this::isCombinationWinning).findFirst();
        return optional.orElseGet(ArrayList::new);
    }

    private boolean containsFieldWithNoMark(List<Field> list) {
        return list.stream().map(Field::getMark).anyMatch(Objects::isNull);
    }

    private boolean areNonNullObjectsEqual(List<Field> list) {
        return list.stream().map(Field::getMark).filter(Objects::nonNull).distinct().count() == 1;
    }
}
