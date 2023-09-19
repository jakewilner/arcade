/**
 * This is a subclass of the platform class which makes a blue platform, which moves back and forth based on the
 * timeline. This class needs to be associated with the doodle class and the gamePane from the PaneOrganizer class
 * so that it can access the timeline, and be added graphically to the game.
 */
package arcade.doodlejump;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BluePlatform extends Platform {

    private int movement;
    private Doodle doodle;

    public BluePlatform(Pane gamePane, Doodle myDoodle) {
        super(gamePane);
        this.setColor(Color.BLUE);
        this.movement = this.randomStart();
        this.doodle = myDoodle;
        this.blueTimeline();
    }

    /**
     * Blue platforms move back and forth, so this platform needs a method to cause it to change directions and move.
     * This method moves the platform back and forth according to the timeline (called in the blueTimeline method)
     * This method checks whether the blue is on screen, and if the platformn is about to go offscreen, it changes the
     * direction of the movement.
     */
    public void blueMove(){
        if (!Doodle.paused) {
            if (this.getX() <= 0 || this.getX() >= Constants.SCREEN_WIDTH - Constants.PLATFORM_WIDTH) {
                this.movement *= -1;
            }
            this.setX(getX() + this.movement);
        }
    }

    /**
     * This adds the blueMove method to the timeline in the Doodle class by using the getTimeline accessor method as
     * well as creating a keyframe and calling the blueMove method within it.
     */
    public void blueTimeline(){
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION),
                (ActionEvent e) -> this.blueMove());
        this.doodle.getTimeline().getKeyFrames().add(kf);
    }

    /**
     * Decides which direction the blue platform will start going in. The random number generator generates either
     * 0 or 1, if it generates 0, the platform will go to the right, if it generates a 1 it will go left.
     */
    public int randomStart(){
        int rand = (int) (Math.random()*2);//Generates 0 or 1 to decide the direction.
        if(rand == 0){
            return 1;
        }
        else{
            return -1;
        }
    }

    /**
     * Returns whether the platform is a green platform so when the character bounces off of it, it gives a larger
     * jump than normal. (Used in each of the subclasses of platform to differentiate between them)
     */
    @Override
    public boolean isGreen(){
        return false;
    }

    /**
     * Returns whether the platform is a red platform so when the character bounces off of it, the platform disappears
     * logically and graphically. (Used in each of the subclasses of platform to differentiate between them)
     */
    @Override
    public boolean isRed(){
        return false;
    }

}
