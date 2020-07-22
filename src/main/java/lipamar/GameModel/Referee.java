package lipamar.GameModel;

import lipamar.App;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Referee implements PropertyChangeListener {

    private UngroupedBoard<Field> board;
    private boolean gameOver = false;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Board.BOARD) && evt.getNewValue() != null) {
            board = new UngroupedBoard<>((List<List<Field>>) evt.getNewValue());
            gameOver = findWinner();
            if (!gameOver) App.GAME.getTurn().next();
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    private boolean findWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        return board.getRows().stream().anyMatch(this::isCombinationWinning);
    }

    private boolean isCombinationWinning(List<Field> list) {
        return !containsFieldWithNoMark(list) && areNonNullObjectsEqual(list);
    }

    private boolean containsFieldWithNoMark(List<Field> list){
        return list.stream().map(Field::getMark).anyMatch(Objects::isNull);
    }
    private boolean areNonNullObjectsEqual(List<Field> list) {
        return list.stream().map(Field::getMark).filter(Objects::nonNull).distinct().count() == 1;
    }

    private boolean checkColumns() {
        return board.getColumns().stream().anyMatch(this::isCombinationWinning);
    }

    private boolean checkDiagonals() {
        return isCombinationWinning(board.getDiagonal1()) || isCombinationWinning(board.getDiagonal2());
    }
}
