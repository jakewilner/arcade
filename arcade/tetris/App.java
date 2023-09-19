package arcade.tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    /**
     * Top level class, begins the game
    */
    @Override
    public void start(Stage stage) {
        PaneOrganizer organizer = new PaneOrganizer(stage);
        Scene scene = new Scene(organizer.getRoot(),
                (Constants.SQUARE_SIZE+ Constants.SQUARE_SPACE)* Constants.WIDTH_MULTIPLIER +
                        Constants.SQUARE_SPACE + Constants.WIDTH_OFFSET,
                (Constants.SQUARE_SIZE+ Constants.SQUARE_SPACE)* Constants.HEIGHT_MULTIPLIER);
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
    }

    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
