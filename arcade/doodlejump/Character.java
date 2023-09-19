package arcade.doodlejump;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class controls movement of the main character sprite and collision detection
 * It is associated with the gamePane and doodle, and helps with the control of the movement vectors
 * It also addresses the keyEvents that control the movement of the main sprite
 */

public class Character {

    private Rectangle character; //rectangle for character sprite
    private Pane gamePane; //pane for association
    private Doodle doodle; //doodle for association
    private Platform tempPlatform; //stores platform upon collision
    private Label paused;
    private boolean aKeyHeld; //boolean flags for movement
    private boolean dKeyHeld;

    public Character(Pane myGamePane, Doodle myDoodle) {
        this.paused = new Label("Paused");
        this.paused.setLayoutX(Constants.SCREEN_WIDTH/2);
        this.paused.setLayoutY(Constants.SCREEN_HEIGHT/2);
        this.gamePane = myGamePane;
        this.character = new Rectangle(Constants.DOODLE_WIDTH, Constants.DOODLE_HEIGHT);
        this.character.setFill(Color.ORANGE); //sets up character color/size
        this.gamePane.getChildren().add(this.character);
        this.character.setX(Constants.INITIAL_X); //sets initial position
        this.character.setY(Constants.INITIAL_Y);
        this.tempPlatform = null;
        this.doodle = myDoodle; //associates doodle
        this.aKeyHeld = false;
        this.dKeyHeld = false;
    }

    /**
     * Method that moves the character horizontally or vertically based on whether the A(Left) or D(Right) key is
     * held. Additionally it checks whether the character and platform has collided and that the character is moving
     * downward, then it checks whether it is a green or a red platform and either provides a big bounce or removes
     * the platform accordingly, or if it is neither of those platform types then it simply gives a normal bounce.
     */
    public void move (double vectorX, double vectorY) { //adjusts the vectors acting on the character
        if (aKeyHeld) {
            vectorX = -3;
        }
        else if (dKeyHeld) {
            vectorX = 3;
        }
        this.character.setX(this.character.getX() + vectorX); //causes movement according to vectors
        this.character.setY(this.character.getY() + vectorY);
        if (this.doodle.getVectorY() > 0 && collision()) { //checks for collision and checks platform type
            if (this.tempPlatform.isGreen()) { //sets bounce to bouncy rebound
                this.doodle.setVectorY(Constants.BOUNCY_REBOUND_VELOCITY);
            }
            else if(this.tempPlatform.isRed()) { //removes platform
                this.tempPlatform.disappear(this.tempPlatform);
                this.doodle.setVectorY(Constants.REBOUND_VELOCITY);
            }
            else { this.doodle.setVectorY(Constants.REBOUND_VELOCITY); }
        }
        this.wrap(); //checks wrap condition, wraps if true
    }

    /**
     * checks if character should move, returns a boolean value
     */
    public boolean charShouldMove(double vectorY) {
        if (this.character.getY() <= Constants.Y_MIN && vectorY < 0) { return false; }
        else { return true; }
    }

    /**
     * Checks for the collision of the character and the platform. Uses a for each loop to check for each individual
     * platform in the platform list, then checks if the character's height is colliding with the top of the platform,
     * as well as if the character is between the X bounds of the platform, then returns true if the character bounces
     * off of the platform properly, and sets the tempPlatform to the platform that has been collided with. This
     * tempPlatform and the boolean is used within the move method to jump properly
     */
    private boolean collision() { //checks collision
        for(Platform platform : this.doodle.getPlatformList()){
            if (this.character.getX() + Constants.DOODLE_WIDTH > platform.getX() && //conditions for collision
            this.character.getX() < platform.getX() + Constants.PLATFORM_WIDTH &&
            this.character.getY() + Constants.DOODLE_HEIGHT < platform.getY() + Constants.PLATFORM_HEIGHT &&
            this.character.getY() + Constants.DOODLE_HEIGHT > platform.getY()) {
                this.tempPlatform = platform; //sets tempPlatform to the collision platform
                return true; //returns true for collision
            }
        }
        return false; //returns false for collision
    }

    /**
     * If the character goes off the screen, then the character spawns to the other side of the screen (no matter
     * what side of the screen you are traveling to)
     */
    private void wrap() { //when character reaches either side, set them to the other side
        if (character.getX() > Constants.RIGHT_BOUND) { character.setX(0); }
        if (character.getX() + Constants.DOODLE_WIDTH < 0) { character.setX(Constants.RIGHT_BOUND); }
    }

    /**
     * Controls the key controls of the character. Overloaded so that LEFT and A do the same action and RIGHT and D
     * do the same action. Each of the keys causes the aKeyHeld and dKeyHeld boolean instance variables to toggle
     * between true and false depending on whether they are pressed or released.
     */
    public void keyPress(KeyEvent e) { //sets up action events, for keystrokes/buttons
        //uses flag booleans to communicate keystrokes
        if (!Doodle.gameOver) {
            if (e.getCode() == KeyCode.P && Doodle.paused) {
                Doodle.paused = false;
                this.aKeyHeld = false;
                this.dKeyHeld = false;
                this.gamePane.getChildren().remove(this.paused);
            } else if (e.getCode() == KeyCode.P) {
                Doodle.paused = true;
                this.aKeyHeld = false;
                this.dKeyHeld = false;
                this.gamePane.getChildren().add(this.paused);
            }
            if (!Doodle.paused) {
                if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
                    this.aKeyHeld = true;
                    this.dKeyHeld = false;
                    e.consume();
                } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                    this.dKeyHeld = true;
                    this.aKeyHeld = false;
                    e.consume();
                }
                this.gamePane.setFocusTraversable(true);
            }
        }
    }

    public void keyRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
            this.aKeyHeld = false;
            e.consume();
        } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            this.dKeyHeld = false;
            e.consume();
        }
    this.gamePane.setFocusTraversable(true);
    }

    /**
     * Accessor method for the Y value of the character rectangle instance for the doodle class to detect
     * whether it is offscreen.
     */
    public double getY(){ return this.character.getY(); } //accessor method for character Y position

}