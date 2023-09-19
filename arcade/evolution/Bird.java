package arcade.evolution;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * Represents and controls much of the bird logic
 */

public class Bird {

    private double gravity;
    private Circle body;
    private Circle eye;
    private Circle pupil;
    private Polygon beak;
    private Pane gamePane;
    private double[][] syn0;
    private double[] syn1;
    public static int fitness;
    private boolean isAlive;
    private int finalFitness;
    private ArrayList<Bird> bestList;

    /**
     * initializes all of the components of the bird, and takes in copies of the weights passed in
     * associates bestList with the bird, allowing for the saving of the best birds
     */
    public Bird(Pane myGamePane, double[][] w0, double[] w1, ArrayList<Bird> myBestList) {
        Game.birdsAlive++;
        this.fitness = 0;
        this.isAlive = true;
        this.gamePane = myGamePane;
        this.bestList = myBestList;
        this.gravity = 0;
        this.shapeSetup();
        this.syn0 = w0.clone();
        this.syn1 = w1.clone();
    }

    /**
     * Sets up graphical representation of bird
     */
    private void shapeSetup() {
        this.body = new Circle(Constants.BIRD_X, Constants.BIRD_Y, Constants.BIRD_RADIUS);
        this.eye = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.EYE_RADIUS);
        this.pupil = new Circle(Constants.BIRD_X+ Constants.EYE_OFFSET_X,
                Constants.BIRD_Y+ Constants.EYE_OFFSET_Y, Constants.PUPIL_RADIUS);
        this.beak = new Polygon(0,0,Constants.POINT_2,Constants.POINT_1,0,Constants.POINT_2);
        this.beak.setFill(Color.ORANGE);
        this.beak.setLayoutX(Constants.BIRD_X+ Constants.BEAK_OFFSET_X);
        this.beak.setLayoutY(Constants.BIRD_Y+ Constants.BEAK_OFFSET_Y);
        this.body.setOpacity(Constants.BIRD_OPACITY);
        this.eye.setOpacity(Constants.BIRD_OPACITY);
        this.pupil.setOpacity(Constants.BIRD_OPACITY);
        this.beak.setOpacity(Constants.BIRD_OPACITY);
        this.eye.setFill(Color.WHITE);
        this.gamePane.getChildren().addAll(this.body, this.eye, this.pupil, this.beak);
    }

    /**
     * Controls bird movement, using an array of input values
     */

    public void move(double[] input) {
        if (this.isAlive) {
            this.gravity += Constants.GRAVITY;
            input[input.length-1] = this.body.getCenterY()/ Constants.SCENE_HEIGHT;
            this.think(input);
            if (this.body.getCenterY() < 0) {
                this.gravity = 1;
            } else if (this.body.getCenterY() > Constants.SCENE_HEIGHT) {
                this.die();
            }
            this.setPos();
        } else {
            this.gamePane.getChildren().remove(this.body);
        }
    }

    /**
     * Performs all matrix operations, dot products, and uses signmoid function to normalize results
     * If final output is greater than the output threshold, the bird jumps
     */
    private void think(double[] input) {
        double[] hid = {0,0,0,0,0};
        double out = 0;
        for (int i = 0; i < hid.length; i++) {
            for (int j = 0; j < input.length; j++) {
                hid[i] += input[j]*this.syn0[i][j];
            }
            hid[i] = this.sigmoid(hid[i]);
        }
        for (int i = 0; i < hid.length; i++) {
            out += hid[i]*this.syn1[i];
        }
        out = this.sigmoid(out);
        if (out > Constants.JUMP_THRESHOLD) { this.jump(); }
    }

    //sigmoid function
    private double sigmoid(double input) {
        input = 1 / (1 + Math.exp(-input));
        return input;
    }

    /**
     * Performs all actions required when bird dies
     * Graphical removal, counter updates, adding to bestList if applicable
     */
    public void die() {
        if (this.isAlive) {
            if (Game.birdsAlive < Constants.MAX_LIST_SIZE+1) {
                if (this.bestList.size() >= Constants.MAX_LIST_SIZE) {
                    this.bestList.remove(0);
                }
                this.bestList.add(this);
            }
            Game.birdsAlive--;
            this.isAlive = false;
            this.remove();
            this.finalFitness = this.fitness;
        }
    }

    //sets gravity to jump
    private void jump() { this.gravity = Constants.JUMP; }

    //sets position of all composite shapes
    private void setPos() {
        this.body.setCenterY(this.body.getCenterY()+this.gravity);
        this.eye.setCenterY(this.eye.getCenterY()+this.gravity);
        this.pupil.setCenterY(this.pupil.getCenterY()+this.gravity);
        this.beak.setLayoutY(this.beak.getLayoutY()+this.gravity);
    }
    //returns Bounds of bird
    public Bounds getBounds() { return this.body.getLayoutBounds(); }

    //removes all components of bird graphically
    public void remove() { this.gamePane.getChildren().removeAll(this.body, this.eye, this.pupil, this.beak); }

    //returns weights (at a given index)/fitness
    public double getSyn0(int row, int col) { return this.syn0[row][col]; }
    public double getSyn1(int index) { return this.syn1[index]; }
    public int getFitness() { return this.finalFitness; }
}