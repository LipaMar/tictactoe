package lipamar.GameModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

class Board {
    public static String BOARD = "board";
    private final ArrayList<ArrayList<Field>> board;
    private final PropertyChangeSupport propertyChangeSupport;

    public Board() {
        board = new ArrayList<>();
        initializeBoardFields();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    private void initializeBoardFields() {
        for (int i = 0; i < 3; i++) {
            ArrayList<Field> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(new Field(i,j));
            }
            board.add(row);
        }
    }

    public boolean isFull(){
        return board.stream().flatMap(Collection::stream).noneMatch(field->field.getMark()==null);
    }

    public List<List<Field>> toList() {
        return Collections.unmodifiableList(board);
    }

    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    public void setMarkOnField(Mark mark, int x, int y) {
        if (board.get(x).get(y).getMark() == null) {
            board.get(x).get(y).setMark(mark);
            propertyChangeSupport.firePropertyChange(BOARD, null, this);
        }
    }

}