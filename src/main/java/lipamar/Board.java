package lipamar;

import java.io.File;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Field>> board;

    public Board() {
        board = new ArrayList<>();
    }

    public Field getField(int x, int y) {
        return board.get(x).get(y);
    }

    private void initializeBoardFields() {
        for (int i = 0; i < 3; i++) {
            ArrayList<Field> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(new Field(i, j));
            }
            board.add(row);
        }
    }
}
