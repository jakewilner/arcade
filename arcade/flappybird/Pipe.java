package arcade.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the pipe, controls its movement, etc.
 */

public class Pipe {

    private Rectangle bottom;
    private Rectangle top;
    private Pane gamePane;

    /**
     * Takes in a center value, associates with gamePane
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
    //moves top and bottom
    public void move() {
        this.bottom.setX(this.bottom.getX()- Constants.PIPE_MOVEMENT);
        this.top.setX(this.top.getX()- Constants.PIPE_MOVEMENT);
    }
    //returns pipe X
    public double getX() { return this.bottom.getX(); }
    //removes pipe
    public void remove() {
        this.gamePane.getChildren().removeAll(this.bottom, this.top);
        this.bottom = null;
        this.top = null;
    }

    /**
     * Returns the bounds, stored in an array
     */
    public Bounds[] getBounds() {
        Bounds[] bounds = new Bounds[2]; //intentionally left without constant, readability
        bounds[0] = this.bottom.getLayoutBounds();
        bounds[1] = this.top.getLayoutBounds();
        return bounds;
    }
}
