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
import lipamar.GameModel.Game;
import lipamar.GameModel.Mark;
import lipamar.GameModel.Referee;

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
    @FXML
    private Button newGameButton;

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
    @FXML
    private void newGame(){
        System.out.println("asdasd");
        App.GAME.newGame();
        gameOverLabel.setVisible(false);
        endBackground.setVisible(false);
        updateBoardView();

    }
    private void cleanup(){

    }

    private void drawWinningLine() {
        List<Field> fields = App.GAME.getReferee().getWinningLine();
        if(fields.isEmpty()){
           return;
        }
        int startX = fields.get(0).getX();
        int startY = fields.get(0).getY();
        int endX = fields.get(fields.size()-1).getX();
        int endY = fields.get(fields.size()-1).getY();
        Line winningLine = generateLine(startX,startY,endX,endY);
        winningLine.getStyleClass().add("line");
        background.getChildren().add(1,winningLine);
    }

    private Line generateLine(int startX, int startY, int endX, int endY) {
        int xDiff = Math.abs(startX - endX);
        int yDiff = Math.abs(startY - endY);
        System.out.printf("sX %d sY%d eX%d eY%d %n",startX, startY, endX, endY);
        System.out.printf("[%f,%f][%f,%f]%n",indexToCoordinates(startY)-(yDiff*25),indexToCoordinates(startX)-(xDiff*25),indexToCoordinates(endY)+(yDiff*25),indexToCoordinates(endX)+(xDiff*25));
        return new Line(indexToCoordinates(startY)-(yDiff*25),indexToCoordinates(startX)-(xDiff*25),
                indexToCoordinates(endY)+(yDiff*25),indexToCoordinates(endX)+(xDiff*25));
    }

    private double indexToCoordinates(int index){
        return index*200+100;
    }

    private void updateBoardView() {
        List<List<Field>> board = App.GAME.getBoard().toList();

        for (int i=0;i<board.size();i++) {
            for (int j=0;j<board.get(i).size();j++) {
                Mark mark = board.get(i).get(j).getMark();
                if ( mark != null)
                    this.fields[i][j].setText(mark.getSign());
                else{
                    this.fields[i][j].setText("");
                }
            }
        }
    }
}

