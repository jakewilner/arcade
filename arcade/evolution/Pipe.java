package arcade.evolution;


import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that represents and controls the movement of the Pipes
 */

public class Pipe {

    private Rectangle bottom;
    private Rectangle top;
    private Pane gamePane;
    private double center;

    /**
     * Creates a pipe at a given center value, associates with gamePane
     */
    public Pipe(double myCenter, Pane myGamePane) {
        this.center = myCenter;
        this.gamePane = myGamePane;
        this.bottom = new Rectangle(Constants.PIPE_WIDTH,
                Constants.SCENE_HEIGHT-this.center- Constants.PIPE_OFFSET);
        this.top = new Rectangle(Constants.PIPE_WIDTH, this.center- Constants.PIPE_OFFSET);
        this.bottom.setX(Constants.PIPE_INITIAL);
        this.bottom.setY(this.center+ Constants.PIPE_OFFSET);
        this.top.setX(Constants.PIPE_INITIAL);
        this.top.setY(0);
        this.top.setFill(Color.BLACK);
        myGamePane.getChildren().addAll(this.top, this.bottom);
    }
    //moves pipes
    public void move() {
        this.bottom.setX(this.bottom.getX()- Constants.PIPE_MOVEMENT);
        this.top.setX(this.top.getX()- Constants.PIPE_MOVEMENT);
    }
    //removes pipe
    public void remove() {
        this.gamePane.getChildren().removeAll(this.bottom, this.top);
        this.bottom = null;
        this.top = null;
    }
    //returns an array for the bounds of the pipe
    public Bounds[] getBounds() {
        Bounds[] bounds = new Bounds[2]; //left without constant for readability
        bounds[0] = this.bottom.getLayoutBounds();
        bounds[1] = this.top.getLayoutBounds();
        return bounds;
    }
    //returns positional data
    public double getX() { return this.bottom.getX(); }
    public double getCenter() { return this.center; }
}
