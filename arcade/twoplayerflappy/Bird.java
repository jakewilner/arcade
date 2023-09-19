package arcade.twoplayerflappy;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * Represents the Bird, controls the bird's movement
 */

public class Bird {

    private double gravity;
    private Circle body;
    private Circle eye;
    private Circle pupil;
    private Polygon beak;
    private Pane gamePane;
    private Boolean isAlive;
    private static int birds = 0;

    /**
     * Sets up the graphical parts of the bird, associates with the gamePane
     */
    public Bird(Pane myGamePane) {
        Bird.birds++;
        this.isAlive = true;
        this.gamePane = myGamePane;
        this.gravity = 0;
        this.body = new Circle(Constants.BIRD_X, Constants.BIRD_Y, Constants.BIRD_RADIUS);
        if (this.birds % 2 == 0) { this.body.setFill(Color.RED); }
        //intentionally left without Constant for readability/clarity
        this.eye = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.EYE_RADIUS);
        this.pupil = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.PUPIL_RADIUS);
        this.beak = new Polygon(0,0, Constants.POINT_2, Constants.POINT_1,0, Constants.POINT_2);
        this.beak.setFill(Color.ORANGE);
        this.beak.setLayoutX(Constants.BIRD_X+ Constants.BEAK_OFFSET_X);
        this.beak.setLayoutY(Constants.BIRD_Y+ Constants.BEAK_OFFSET_Y);
        this.eye.setFill(Color.WHITE);
        this.body.setOpacity(Constants.OPACITY);
        this.eye.setOpacity(Constants.OPACITY);
        this.pupil.setOpacity(Constants.OPACITY);
        this.beak.setOpacity(Constants.OPACITY);
        this.gamePane.getChildren().addAll(this.body, this.eye, this.pupil, this.beak);
    }

    /**
     * Moves bird and checks edge conditions, maintains counters
     */
    public void move() {
        this.gravity += Constants.GRAVITY;
        if (this.body.getCenterY() < 0) { this.gravity = 1; }
        else if (this.body.getCenterY() > Constants.SCENE_HEIGHT && this.isAlive) {
            Game.birdsDead++;
            this.isAlive = false;
            this.remove();
        }
        this.setPos();
    }
    //causes bird to jump
    public void jump() { this.gravity = Constants.JUMP; }
    //sets bird position
    private void setPos() {
        this.body.setCenterY(this.body.getCenterY()+this.gravity);
        this.eye.setCenterY(this.eye.getCenterY()+this.gravity);
        this.pupil.setCenterY(this.pupil.getCenterY()+this.gravity);
        this.beak.setLayoutY(this.beak.getLayoutY()+this.gravity);
    }
    //returns the bounds
    public Bounds getBounds() { return this.body.getLayoutBounds(); }
    //removes bird graphically
    public void remove() {
        this.gamePane.getChildren().removeAll(this.body, this.eye, this.pupil, this.beak);
    }
    //setter and getter for isAlive
    public void setAlive(Boolean set) { this.isAlive = set; }
    public Boolean getIsAlive() { return this.isAlive; }
}