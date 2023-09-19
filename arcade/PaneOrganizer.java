package arcade;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneOrganizer {

    private BorderPane root;
    private VBox arcadePane;
    private Button manualButton;
    private Button multiplayerButton;
    private Button smartButton;
    private Button tetrisButton;
    private Button doodleButton;
    private Button quit;

    /**
     * Prepares buttons to launch each game's App class
     * Chose to use App classes, rather than PaneOrganizers, for maximum extensibility and abstraction
     * Also allows arcade to continue running in the background, so I can launch multiple games at once
     * That way, my birds can learn in the background :)
     */
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.arcadePane = new VBox();
        this.arcadePane.setAlignment(Pos.CENTER);
        this.manualButton = new Button("Manual Flappy Bird");
        this.multiplayerButton = new Button("Two-Player Flappy Bird");
        this.smartButton = new Button("Smart Flappy Bird");
        this.tetrisButton = new Button("Tetris");
        this.doodleButton = new Button("Doodle Jump");
        this.quit = new Button("Quit");
        this.root.setCenter(this.arcadePane);
        this.arcadePane.getChildren().addAll(this.manualButton, this.multiplayerButton,
                this.smartButton, this.tetrisButton, this.doodleButton, this.quit);
        this.setUpAction();
    }

    /** Prepares button actions */
    private void setUpAction() {
        this.manualButton.setOnAction((ActionEvent e) -> new arcade.flappybird.App().start(new Stage()));
        this.multiplayerButton.setOnAction((ActionEvent e) -> new arcade.twoplayerflappy.App().start(new Stage()));
        this.smartButton.setOnAction((ActionEvent e) -> new arcade.evolution.App().start(new Stage()));
        this.tetrisButton.setOnAction((ActionEvent e) -> new arcade.tetris.App().start(new Stage()));
        this.doodleButton.setOnAction((ActionEvent e) -> new arcade.doodlejump.App().start(new Stage()));
        this.quit.setOnAction((ActionEvent e) -> System.exit(0));
    }

    /** Returns root */
    public BorderPane getRoot() {
        return this.root;
    }
}
