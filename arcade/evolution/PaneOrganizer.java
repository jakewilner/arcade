package arcade.evolution;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Top level graphical class, contains Panes/Buttons/Labels
 */

public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private HBox labelPane;
    private Label score;
    private Button restart;
    private Button quit;
    private Button back;
    private Game game;
    private Button maxSpeed;
    private Button singleSpeed;

    /**
     * Initializes various buttons/labels/panes
     */
    public PaneOrganizer(Stage stage) {
        this.restart = new Button("Restart");
        this.quit = new Button("Quit");
        this.back = new Button("Back");
        this.maxSpeed = new Button("MAX");
        this.singleSpeed = new Button("1x");
        this.score = new Label();
        this.root = new BorderPane();
        this.gamePane = new Pane();
        this.labelPane = new HBox();
        this.labelPane.setStyle("-fx-background-color: #bdbdbd");
        this.labelPane.setSpacing(Constants.PANE_SPACING);
        this.labelPane.getChildren().addAll(
                this.score, this.quit, this.back, this.restart, this.maxSpeed, this.singleSpeed);
        this.game = new Game(this.gamePane, this.labelPane);
        this.root.setCenter(this.gamePane);
        this.root.setTop(this.labelPane);
        this.setAction(stage);
        this.gamePane.setFocusTraversable(true);
        this.labelPane.setFocusTraversable(false);
    }
    //prepares button actions
    private void setAction(Stage stage) {
        this.quit.setOnAction((ActionEvent e) -> System.exit(0));
        this.restart.setOnAction((ActionEvent e) -> this.game.reset());
        this.back.setOnAction((ActionEvent e) -> stage.hide());
        this.singleSpeed.setOnAction((ActionEvent e) -> this.game.setUpTimeline(Constants.STANDARD));
        this.maxSpeed.setOnAction((ActionEvent e) -> this.game.setUpTimeline(Constants.DURATION));
    }
    //returns root
    public BorderPane getRoot() {
        return this.root;
    }
}
