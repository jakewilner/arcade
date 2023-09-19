package arcade.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * Graphical representation and logic for the bird
 */

public class Bird {

    private double gravity;
    private Circle body;
    private Circle eye;
    private Circle pupil;
    private Polygon beak;
    private Pane gamePane;

    /**
     * Associates gamePane with bird, instantiates all graphical components
     */
    public Bird(Pane myGamePane) {
        this.gamePane = myGamePane;
        this.gravity = 0;
        this.body = new Circle(Constants.BIRD_X, Constants.BIRD_Y, Constants.BIRD_RADIUS);
        this.eye = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.EYE_RADIUS);
        this.pupil = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.PUPIL_RADIUS);
        this.beak = new Polygon(0,0, Constants.POINT_2, Constants.POINT_1,0, Constants.POINT_2);
        this.beak.setFill(Color.ORANGE);
        this.beak.setLayoutX(Constants.BIRD_X+ Constants.BEAK_OFFSET_X);
        this.beak.setLayoutY(Constants.BIRD_Y+ Constants.BEAK_OFFSET_Y);
        this.eye.setFill(Color.WHITE);
        this.gamePane.getChildren().addAll(this.body, this.eye, this.pupil, this.beak);
    }

    /**
     * Moves bird, checks edge conditions
     */
    public void move() {
        this.gravity += Constants.GRAVITY;
        if (this.body.getCenterY() < 0) { this.gravity = 1; }
        else if (this.body.getCenterY() > Constants.SCENE_HEIGHT) { Game.gameOver = true; }
        this.setPos();
    }
    //sets bird to jump
    public void jump() { this.gravity = Constants.JUMP; }

    //sets position of graphical elements
    private void setPos() {
        this.body.setCenterY(this.body.getCenterY()+this.gravity);
        this.eye.setCenterY(this.eye.getCenterY()+this.gravity);
        this.pupil.setCenterY(this.pupil.getCenterY()+this.gravity);
        this.beak.setLayoutY(this.beak.getLayoutY()+this.gravity);
    }
    //returns the bounds of the bird
    public Bounds getBounds() { return this.body.getLayoutBounds(); }

    //removes all components of bird graphically
    public void remove() {
        this.gamePane.getChildren().removeAll(this.body, this.eye, this.pupil, this.beak);
    }
}
