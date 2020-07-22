package lipamar.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import lipamar.App;
import lipamar.GameModel.Field;
import lipamar.GameModel.Mark;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class boardViewController implements Initializable {
    private final int SIDE = 3;
    private Button[][] fields;
    @FXML
    private GridPane grid;
    @FXML
    private Label gameOverLabel;
    @FXML
    private Pane endBackground;
    @FXML
    private Pane background;

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
                    if(App.GAME.getReferee().isGameOver())
                    {
                        gameOverLabel.setVisible(true);
                        endBackground.setVisible(true);
                        drawWinningLine();
                    }


                });
                GridPane.setHalignment(button, HPos.CENTER);
                GridPane.setValignment(button, VPos.CENTER);
                fields[i][j] = button;
            }
        }

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                grid.add(fields[i][j], j, i);
            }
        }
    }

    private void drawWinningLine() {
        Line winningLine = new Line(100,100,400,400);
        winningLine.getStyleClass().add("line");
        background.getChildren().add(1,winningLine);
    }

    private void updateBoardView() {
        List<List<Field>> board = App.GAME.getBoard().toList();

        for (int i=0;i<board.size();i++) {
            for (int j=0;j<board.get(i).size();j++) {
                Mark mark = board.get(i).get(j).getMark();
                if ( mark != null)
                    this.fields[i][j].setText(mark.getSign());
            }
        }
    }
}

