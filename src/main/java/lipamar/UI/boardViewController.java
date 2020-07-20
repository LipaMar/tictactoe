package lipamar.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lipamar.App;
import lipamar.Board;
import lipamar.Field;
import lipamar.Mark;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class boardViewController implements Initializable {
    private final int SIDE = 3;
    private Button[][] fields;
    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fields = new Button[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Button button = new Button();
                int finalI = i;
                int finalJ = j;
                button.setOnAction(actionEvent -> {
                    App.GAME.makeAMove(finalI, finalJ);
                    updateBoardView();
                });
                GridPane.setHalignment(button, HPos.CENTER);
                GridPane.setValignment(button, VPos.CENTER);
                fields[i][j] = button;
            }
        }

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                grid.add(fields[i][j], i, j);
            }
        }
    }

    private void updateBoardView() {
        List<List<Field>> board = App.GAME.getBoard().toList();

        for (List<Field> fields : board) {
            for (Field field : fields) {
                Mark mark = field.getMark();
                if (mark != null)
                    this.fields[field.getX()][field.getY()].setText(mark.getSign());
            }
        }
    }
}

