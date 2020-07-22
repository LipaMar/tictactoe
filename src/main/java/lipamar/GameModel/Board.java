package lipamar.GameModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    public static String BOARD = "board";
    private final ArrayList<ArrayList<Field>> board;
    private final PropertyChangeSupport propertyChangeSupport;

    public Board(PropertyChangeListener referee) {
        board = new ArrayList<>();
        initializeBoardFields();
        propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(referee);
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

    public void setMarkOnField(Mark mark, int x, int y) {
        if (board.get(x).get(y).getMark() == null) {
            board.get(x).get(y).setMark(mark);
            propertyChangeSupport.firePropertyChange(BOARD, null, this.toList());
        }
    }

    public List<List<Field>> toList() {
        return Collections.unmodifiableList(board);
    }

}