package lipamar.UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
                Button button = new Button("o");
                int finalI = i;
                int finalJ = j;
                button.setOnAction(actionEvent -> {
                    System.out.println("i = " + finalI + " j = " + finalJ);
                    button.setText("x");
                });
                grid.setHalignment(button, HPos.CENTER);
                grid.setValignment(button, VPos.CENTER);
                fields[i][j] = button;
            }
        }

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                grid.add(fields[i][j], i, j);
            }
        }

    }

}

