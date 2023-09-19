package arcade.twoplayerflappy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Prepares panes, buttons, labels
 */

public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private HBox labelPane;
    private Label score;
    private Label highScore;
    private Button restart;
    private Button quit;
    private Button back;
    private Game game;

    /**
     * Sets up buttons, labels and panes
     */
    public PaneOrganizer(Stage stage) {
        this.restart = new Button("Restart");
        this.quit = new Button("Quit");
        this.back = new Button("Back");
        this.score = new Label();
        this.highScore = new Label();
        this.root = new BorderPane();
        this.gamePane = new Pane();
        this.labelPane = new HBox();
        this.labelPane.setStyle("-fx-background-color: #bdbdbd");
        this.labelPane.setSpacing(Constants.PANE_SPACING);
        this.labelPane.getChildren().addAll(this.score, this.highScore, this.quit, this.back, this.restart);
        this.game = new Game(this.gamePane, this.score, this.highScore);
        this.root.setCenter(this.gamePane);
        this.root.setTop(this.labelPane);
        this.quit.setOnAction((ActionEvent e) -> System.exit(0));
        this.restart.setOnAction((ActionEvent e) -> this.reset());
        this.back.setOnAction((ActionEvent e) -> stage.hide());
        this.gamePane.setFocusTraversable(true);
        this.labelPane.setFocusTraversable(false);
        this.restart.setFocusTraversable(false);
        this.back.setFocusTraversable(false);
        this.quit.setFocusTraversable(false);
    }
    //returns root
    public BorderPane getRoot() {
        return this.root;
    }
    //resets game
    public void reset() {
        this.game.reset();
        this.labelPane.getChildren().removeAll(this.labelPane);
        this.game = new Game(this.gamePane, this.score, this.highScore);
        this.gamePane.requestFocus();
    }
}
