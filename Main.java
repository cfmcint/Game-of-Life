import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Clara
 * @version 1.0 12/10/19
 *
 * Creates the Game of Life game
 */
public class Main extends Application {
    private static VBox rootNode = new VBox(10);
    private static int width = 80;
    private static int height = 40;
    private static ToggleButton pP = new ToggleButton("Play");
    private static Button back = new Button("<< Back");
    private static Button forward = new Button ("Forward >>");
    private static Square mySquare[][] = new Square[width][height];
    private KeyFrame kf = new KeyFrame(Duration.millis(200), event -> goForward());
    private Timeline tl = new Timeline(kf);
    private static int count = 0;

    /**
     * Formats the rootNode. Adds and calls the methods to format hb and gBoard
     */
    private void paneFormat(){
        HBox hb = new HBox(30);
        GridPane gBoard = new GridPane();

        gBoard.setAlignment(Pos.CENTER);
        gBoard.setHgap(2);
        gBoard.setVgap(2);

        hb.setAlignment(Pos.CENTER);
        hb.setStyle("-fx-background-color: BLACK");
        hb.setPadding(new Insets(10,0,10,0));

        rootNode.setStyle("-fx-background-color: WHITE");
        rootNode.setAlignment(Pos.BOTTOM_CENTER);

        formatGBoard(gBoard);
        formatHb(hb, gBoard);

        rootNode.getChildren().addAll(gBoard, hb);
    }

    /**
     * @param singSqur a single square in the gBoard
     *
     * changes the color of the square and changes getOff depending on whether it is on or off
     */
    private static void changeColor(Square singSqur) {

        if (singSqur.getOff()) {
            singSqur.getSqr().setFill(Color.BLACK);
            singSqur.setOff(false);
        } else if (!singSqur.getOff()) {
            singSqur.getSqr().setFill(Color.PINK);
            singSqur.setOff(true);
        }

    }

    /**
     * @param gBoard GridPane containing the squares
     *
     * Add squares to the board and makes action event for them
     */
    private static void formatGBoard(GridPane gBoard) {
        gBoard.getChildren().clear();

        int i;
        int k;

        for (i = 0; i < width; i++) {
            for (k = 0; k < height; k++) {
                mySquare[i][k] = new Square();
                mySquare[i][k].getSqr().setFill(Color.PINK);
                gBoard.add(mySquare[i][k].getSqr(), i, k);
            }
        }

        for (i = 0; i < width; i++) {
            for (k = 0; k < height; k++) {
                final int y = k;
                final int x = i;

                mySquare[i][k].getSqr().setOnMouseClicked(e -> changeColor(mySquare[x][y]));
            }
        }
    }

    /**
     * @param field a TextField whose value is being manipulated
     * @param newVal a new string value that the TextField is set to if the conditions of the if statement are met
     *
     * checks if a value is an int
     */
    private static void checkInt (TextField field, String newVal) {
        field.setText(field.getText().trim());
        if (!newVal.matches("\\d*")) {
            field.setText(newVal.replaceAll("[^\\d]", ""));
        }
    }

    private void staticScreen() {
        Alert a = new Alert(Alert.AlertType.NONE);

        pP.setText("Play");
        tl.stop();
        count++;

        a.setHeaderText(null);
        a.setContentText("Screen is static");
        ButtonType ok = new ButtonType("OK");
        a.getButtonTypes().clear();

        a.getButtonTypes().add(ok);

        a.show();
    }

    /**
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @return neigh which is the number of neighbors
     *
     * Finds the number of neighbors
     */
    private static int checkNeigh (int x, int y) {
        int neigh = 0;
        int i;
        int k;
        int v_x;
        int v_y;

        for (i = -1; i < 2; i ++) {
            for (k = -1; k < 2; k++) {
                    v_x = ((x + i) % width);
                    v_y = ((y + k) % height);
                    if (v_x == -1) v_x = (width - 1);
                    if (v_y == -1) v_y = (height - 1);

                    if (mySquare[v_x][v_y] != mySquare[x][y] && !mySquare[v_x][v_y].getOff()) neigh++;
            }
        }

        return neigh;
    }

    /**
     * Proceeds one move forward when called
     */
    private void goForward () {
        int i;
        int k;
        boolean same = true;

        for (i = 0; i < width; i++) {
            for (k = 0; k < height; k++) {
                mySquare[i][k].setNeigh(checkNeigh(i,k));
                mySquare[i][k].setWas(mySquare[i][k].getOff());
            }
        }

        for (i = 0; i < width; i++) {
            for (k = 0; k < height; k++) {
                if (!mySquare[i][k].getOff()) {
                    if (mySquare[i][k].getNeigh() < 2 || mySquare[i][k].getNeigh() > 3) changeColor(mySquare[i][k]);
                } else {
                    if (mySquare[i][k].getNeigh() == 3) changeColor(mySquare[i][k]);
                }

                if (mySquare[i][k].getOff() != mySquare[i][k].getWas()) same = false;
            }
        }

        if (same) staticScreen();
    }

    /**
     * @param tf  TextField whose value is being manipulated or used to format the gBoard
     * @param upper Maximum value of the TextField
     * @param gBoard GridPane which contains all of the squares
     *
     * Makes sure the TextField contains an integer whose value cannot be greater than the upper limit and then formats the gBoard accordingly
     */
    private static void widthHeightActions (TextField tf, int upper, GridPane gBoard) {
        tf.textProperty().addListener((obs, oldVal, newVal) -> {
            checkInt(tf, newVal);

            if (!tf.getText().equals("")) {
                if (Integer.parseInt(tf.getText()) > upper) {
                    tf.setText(oldVal);
                }
            }

        });

        tf.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                if (tf.getText().equals("")) {
                    tf.setText("0");
                }

                width = Integer.parseInt(tf.getText());
                height = Integer.parseInt(tf.getText());

                formatGBoard(gBoard);

            }
        });
    }

    /**
     * @param title large title on screen
     * @param width_L label for the width TextField
     * @param height_L Label for the height TextField
     * @param width_I width TextField
     * @param height_I height TextField
     * @param gBoard GridPane with all of the squares in it
     *
     * Lays out all of the TextFields and Labels on the stage
     */
    private static void formatTexts (Label title, Label width_L, Label height_L, TextField width_I, TextField height_I, GridPane gBoard) {
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        title.setTextFill(Color.PINK);
        width_L.setTextFill(Color.WHITE);
        height_L.setTextFill(Color.WHITE);

        width_I.setPrefWidth(50);
        height_I.setPrefWidth(50);
        width_I.setText("80");
        height_I.setText("40");

        widthHeightActions(width_I, 100, gBoard);
        widthHeightActions(height_I, 49, gBoard);
    }

    /**
     * Allows the user to go back one move
     */
    private static void backButton () {
        int i;
        int k;

        for (i = 0; i < width; i++) {
            for (k = 0; k < height; k++) {
                if (mySquare[i][k].getOff() != mySquare[i][k].getWas()) changeColor(mySquare[i][k]);
            }
        }
    }

    /**
     * @param hb HBox on the bottom of the screen that contains the buttons and other text
     * @param gBoard GridPane that contains all of the squares
     *
     * Includes action events for the play and forward button
     * Adds children to hb
     */
    private void formatHb(HBox hb, GridPane gBoard) {
        Label title = new Label("Game of Life");
        Label width_L = new Label ("Width: ");
        Label height_L = new Label ("Height: ");
        TextField width_I = new TextField();
        TextField height_I = new TextField();

        formatTexts(title, width_L, height_L, width_I, height_I, gBoard);

        forward.setOnAction(e ->  {
            tl.setCycleCount(1);
            tl.play();
        });

        pP.setOnAction(e -> {
            if (count % 2 == 0) {
                pP.setText("Pause");
                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();
                count ++;
            } else {
                pP.setText("Play");
                tl.stop();
                count++;
            }
        });

        back.setOnAction( e -> {
            backButton();
        });

        hb.getChildren().addAll(back, pP, forward, title, width_L, width_I, height_L, height_I);
    }

    /**
     * @param myStage the stage
     * @throws Exception
     *
     * sets Scene
     */
    public void start(Stage myStage) throws Exception{
        myStage.setTitle("Game of Life");
        paneFormat();

        Scene myScene = new Scene(rootNode, 1000, 550);
        myStage.setScene(myScene);
        myStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
