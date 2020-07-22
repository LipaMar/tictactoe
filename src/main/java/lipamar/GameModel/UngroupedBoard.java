package lipamar.GameModel;

import java.util.ArrayList;
import java.util.List;

public class UngroupedBoard<T> {
    private final List<List<T>> rows = new ArrayList<>();
    private final List<List<T>> columns = new ArrayList<>();
    private final List<T> diagonal1;
    private final List<T> diagonal2;

    public UngroupedBoard(List<List<T>> board) {
        int size = board.size();
        List<T> diagonal1 = new ArrayList<>();
        List<T> diagonal2 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<T> row = new ArrayList<>();
            List<T> column = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(board.get(i).get(j));
                column.add(board.get(j).get(i));
                if (i == j)
                    diagonal1.add(board.get(i).get(j));
                if (i + j == board.size() - 1) {
                    diagonal2.add(board.get(i).get(j));
                }
            }
            rows.add(row);
            columns.add(column);
        }
        this.diagonal1 = diagonal1;
        this.diagonal2 = diagonal2;

    }

    public List<List<T>> getRows() {
        return rows;
    }

    public List<List<T>> getColumns() {
        return columns;
    }

    public List<T> getDiagonal1() {
        return diagonal1;
    }

    public List<T> getDiagonal2() {
        return diagonal2;
    }
}
