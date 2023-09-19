package arcade.twoplayerflappy;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the pipe, controls movement
 */

public class Pipe {

    private Rectangle bottom;
    private Rectangle top;
    private Pane gamePane;

    /**
     * Associates with gamePane
     * Creates a pipe at a given center value
     */
    public Pipe(double center, Pane myGamePane) {
        this.gamePane = myGamePane;
        this.bottom = new Rectangle(Constants.PIPE_WIDTH, Constants.SCENE_HEIGHT-center- Constants.PIPE_OFFSET);
        this.top = new Rectangle(Constants.PIPE_WIDTH, center- Constants.PIPE_OFFSET);
        this.bottom.setX(Constants.PIPE_INITIAL);
        this.bottom.setY(center+ Constants.PIPE_OFFSET);
        this.top.setX(Constants.PIPE_INITIAL);
        this.top.setY(0);
        this.top.setFill(Color.BLACK);
        myGamePane.getChildren().addAll(this.top, this.bottom);
    }
    //moves pipe
    public void move() {
        this.bottom.setX(this.bottom.getX()- Constants.PIPE_MOVEMENT);
        this.top.setX(this.top.getX()- Constants.PIPE_MOVEMENT);
    }
    //returns X
    public double getX() { return this.bottom.getX(); }
    //removes pipe graphically
    public void remove() {
        this.gamePane.getChildren().removeAll(this.bottom, this.top);
        this.bottom = null;
        this.top = null;
    }

    /**
     * Returns bounds in an array
     */
    public Bounds[] getBounds() {
        Bounds[] bounds = new Bounds[2]; //intentionally left without constant, readability/clarity
        bounds[0] = this.bottom.getLayoutBounds();
        bounds[1] = this.top.getLayoutBounds();
        return bounds;
    }
}