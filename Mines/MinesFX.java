package Mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MinesFX extends Application {

    Button[][] buttons;
    int minesWidth = 10, minesHeight = 10, minesMines = 10;
    MenuController menucontroler; // TODO: change to variables
    GridPane gridPane;

    public void refreshButtonsStrings() {
        String[][] refreshedStr = menucontroler.getMinesStrings();
        for (int i = 0; i < refreshedStr.length; i++) {
            for (int j = 0; j < refreshedStr[i].length; j++) {
                if (refreshedStr[i][j].equals("1")) {
                    buttons[i][j].setTextFill(Color.RED);
                }
                if (refreshedStr[i][j].equals("2")) {
                    buttons[i][j].setTextFill(Color.BLUE);
                }
                if (refreshedStr[i][j].equals("3")) {
                    buttons[i][j].setTextFill(Color.GREEN);
                }
                if (refreshedStr[i][j].equals("4")) {
                    buttons[i][j].setTextFill(Color.BROWN);
                }
                buttons[i][j].setText(refreshedStr[i][j]);
            }
        }
    }

    public void checkMineHit(int x, int y) {
        if (menucontroler.getHasMine(x, y)) {
            dialogBox("You've lost! Game over.", "Game over", "File:gameover.gif");
        }
    }

    public void initMinesGame() {
        menucontroler = new MenuController(minesWidth, minesHeight, minesMines); // TODO: change to variables
        buttons = new Button[minesWidth][minesHeight];
        gridPane.getChildren().clear();
        Button temp;
        for (int i = 0; i < minesWidth; i++) {
            for (int j = 0; j < minesHeight; j++) {
                String tempValue = menucontroler.get(i, j);
                temp = new Button(tempValue);
                temp.setId(i + "," + j);
                buttons[i][j] = temp;

                temp.setOnMouseClicked(e -> {
                    if (e.getButton().name().equals("PRIMARY")) {
                        String value = ((Button) e.getSource()).getId();
                        int x = Integer.parseInt(value.split(",")[0]);
                        int y = Integer.parseInt(value.split(",")[1]);
                        menucontroler.click(x, y);
                        refreshButtonsStrings();
                        checkMineHit(x, y);
                    } else {
                        String value = ((Button) e.getSource()).getId();
                        int x = Integer.parseInt(value.split(",")[0]);
                        int y = Integer.parseInt(value.split(",")[1]);
                        menucontroler.toggleFlag(x, y);
                        refreshButtonsStrings();
                        if (menucontroler.checkIfWon()) {
                            dialogBox("You won", "CONGRATZ", "File:victory.gif");
                        }
                    }
                });
                temp.setPrefWidth(30);
                temp.setPrefHeight(30);
                gridPane.add(temp, i + 5, j + 20);
            }
        }
    }

    public void disableButtons() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setOnMouseClicked(e -> {
                });
            }
        }
    }

    public void dialogBox(String msg, String title, String picLocation) {
        disableButtons();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        pane.add(new Label(msg), 0, 0);
        Image i = new Image(picLocation);
        pane.add(new ImageView(i), 0, 1);
        Button btOK = new Button("OK");
        pane.add(btOK, 10, 2);
        GridPane.setHalignment(btOK, HPos.RIGHT);
        Stage stage = new Stage();
        btOK.setOnAction(e -> {
            gridPane.getChildren().clear();
            initMinesGame();
            stage.close();
        });
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(pane);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("MinesFX.fxml"));
            root.getChildren();
            Scene scene = new Scene(root, 500, 500);
            gridPane = new GridPane();
            gridPane.setHgap(3);
            gridPane.setVgap(3);
            Pane menuPane = (Pane) root.getChildren().get(0);
            Button resetButton = (Button) menuPane.getChildren().get(0);
            resetButton.setOnAction(e -> {
                try {
                    minesWidth = Integer.parseInt(((TextField) menuPane.getChildren().get(4)).getText());
                    minesHeight = Integer.parseInt(((TextField) menuPane.getChildren().get(5)).getText());
                    minesMines = Integer.parseInt(((TextField) menuPane.getChildren().get(6)).getText());
                    if (minesMines > minesWidth * minesHeight) {
                        return;
                    }
                    gridPane.getChildren().clear();
                    initMinesGame();
                } catch (Exception ex) {

                }
            });
            Pane mainPane = (Pane) root.getChildren().get(1);
            initMinesGame();
            mainPane.getChildren().addAll(gridPane);
            mainPane.setStyle("-fx-background-color: #a7bcff;");
            menuPane.setStyle("-fx-background-color: #a7bcff;");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
