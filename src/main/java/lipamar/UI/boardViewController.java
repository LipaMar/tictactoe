package lipamar.UI;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;
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
    private VBox endBackground;
    @FXML
    private Pane background;
    @FXML
    private Label scores;
    @FXML
    private Label startInfo;
    private Line winningLine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStartInfo();
        fields = new Button[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Button button = new Button();
                int finalI = i;
                int finalJ = j;
                button.setOnAction(actionEvent -> {
                    App.GAME.makeAMove(finalI, finalJ);
                    updateBoardView();
                    if (App.GAME.getReferee().isGameOver()) {
                        drawWinningLine();
                        gameOverLabel.setVisible(true);
                        endBackground.setVisible(true);
                        scores.setText(scoresToString());
                    }


                });
                button.getStyleClass().add("boardButton");
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

    private void showStartInfo() {
        startInfo.setText("First move\n" + App.GAME.getTurn().getMark().getSign().toUpperCase());
        Timeline vanish = nodeVanish(startInfo);
        vanish.play();
    }

    private Timeline nodeVanish(Node node) {
        return new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.visibleProperty(), true
                        )
                ),
                new KeyFrame(
                        Duration.seconds(1.5),
                        new KeyValue(
                                node.visibleProperty(), false
                        )
                ));
    }

    private String scoresToString() {
        var scores = App.GAME.getReferee().getScores();
        int x = scores.get(Mark.CROSS);
        int o = scores.get(Mark.NOUGHT);
        return String.format("%s  %d:%d  %s", Mark.CROSS.getSign(), x, o, Mark.NOUGHT.getSign()).toUpperCase();
    }

    @FXML
    private void newGame() {
        App.GAME.newGame();
        resetUI();

    }

    public void nextGame(ActionEvent actionEvent) {
        App.GAME.nextGame();
        resetUI();
    }

    private void resetUI() {
        background.getChildren().remove(winningLine);
        gameOverLabel.setVisible(false);
        endBackground.setVisible(false);
        updateBoardView();
        showStartInfo();
    }

    private void drawWinningLine() {
        List<Field> fields = App.GAME.getReferee().getWinningLine();
        if (fields.isEmpty()) {
            return;
        }
        int startX = fields.get(0).getX();
        int startY = fields.get(0).getY();
        int endX = fields.get(fields.size() - 1).getX();
        int endY = fields.get(fields.size() - 1).getY();
        winningLine = generateLine(startX, startY, endX, endY);
        winningLine.getStyleClass().add("line");
        background.getChildren().add(1, winningLine);
    }

    private Line generateLine(int startX, int startY, int endX, int endY) {
        int xDiff = Math.abs(startX - endX);
        int yDiff = Math.abs(startY - endY);
        if (startY > endY) {
            yDiff *= -1;
        }
        return new Line(indexToCoordinates(startY) - (yDiff * 25), indexToCoordinates(startX) - (xDiff * 25),
                indexToCoordinates(endY) + (yDiff * 25), indexToCoordinates(endX) + (xDiff * 25));
    }

    private double indexToCoordinates(int index) {
        return index * 200 + 100;
    }

    private void updateBoardView() {
        List<List<Field>> board = App.GAME.getBoard().toList();

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                Mark mark = board.get(i).get(j).getMark();
                if (mark != null)
                    this.fields[i][j].setText(mark.getSign());
                else {
                    this.fields[i][j].setText("");
                }
            }
        }
    }
}

