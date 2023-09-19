/**
 * This is the top level graphical class responsible for managing all of the different graphical elements that are
 * displayed on the screen at once. This includes two instances of Panes, a border Pane, an HBox, a button, a label,
 * and a doodle class. This class contains a bunch of helper methods that help streamline the constructor.
 */
package arcade.doodlejump;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private Pane labelPane;
    private Label scoreLabel;
    private Doodle doodle;
    private Button back;
    private Button restart;

    public PaneOrganizer(Stage stage){
        this.back = this.buttonPrep("Back", Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        this.back.setOnAction((ActionEvent e) -> stage.hide());
        this.restart = this.buttonPrep("Restart", Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        this.restart.setOnAction((ActionEvent e) -> this.reset());
        this.root = new BorderPane();
        this.gamePane = new Pane();
        this.labelPane = new Pane();
        this.root.setCenter(this.gamePane);
        this.root.setTop(this.labelPane);
        HBox buttonPane = new HBox();
        this.setUpButtons(buttonPane);
        this.root.setBottom(buttonPane);
        this.setUpLabel();
        this.doodle = new Doodle(this.gamePane, this.scoreLabel);
        this.gamePane.setFocusTraversable(true);
    }

    /**
     * Accessor method that the root of the PaneOrganizer for future (used to add the other nodes to the root when
     * they are created in other classes)
     */
    public BorderPane getRoot(){
        return this.root;
    }

    /**
     * Helper method that creates the buttons and adds them to a pane
     */
    private void setUpButtons(HBox pane) {
        Button quit = this.buttonPrep("Quit", Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        pane.getChildren().addAll(quit, this.restart, this.back);
        pane.setAlignment(Pos.CENTER);
        pane.setTranslateY(Constants.BUTTON_TRANSLATE);
        quit.setOnAction((ActionEvent e) -> System.exit(0));
    }

    /**
     * Helper method used to create the buttons and set them up with the correct width and height and have the
     * proper text within them
     */
    private Button buttonPrep(String text, int width, int height){
        Button button = new Button();
        button.setText(text);
        button.setPrefSize(width, height);
        button.setFocusTraversable(false);
        return button;
    }

    /**
     * Helper method that creates the scoreLabel and adds it graphically
     */
    private void setUpLabel(){
        this.scoreLabel = new Label("Score: 0");
        this.scoreLabel.setPrefSize(Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.labelPane.getChildren().add(this.scoreLabel);
    }

    private void reset() {
        this.doodle.reset();
        this.labelPane.getChildren().removeAll(this.labelPane);
        this.doodle = new Doodle(this.gamePane, this.scoreLabel);
        Doodle.paused = false;
        this.gamePane.requestFocus();
    }
}
