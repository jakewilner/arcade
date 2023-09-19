/**
 * This is the platform class that is responsible for the rectangles that appear on the screen. This is a super class
 * of the 4 different colors of platforms that each have different properties, so as a result, this class contains
 * methods that all of those classes can use. This includes setting the random displacement of the starting location,
 * moving the platform, or setting the color of the rectangle within the class. This class contains a reference ot a
 * Rectangle, and is associated with and instance of a Pane.
 */
package arcade.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform {
    private Rectangle platform;

    public Platform(Pane myGamePane) {
        this.platform = new Rectangle(Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
        myGamePane.getChildren().add(this.platform);
    }

    /**
     * Moves the platform up and down based on the timeline (this method is only called if the character is halfway
     * up the screen)
     */
    public void move(double vectorY) {
        this.platform.setX(this.platform.getX());
        this.platform.setY(this.platform.getY()+vectorY);
    }

    /**
     * Gets two random values and adds them to the X and Y variables of previous platform to ensure that there
     * is a new platform with a new coordinate based on the last platform.
     */
    public void setDisplacement(Platform previousPlatform) {
        double xDisplacement = (Math.random()* Constants.X_DISPLACEMENT) - Constants.SCREEN_WIDTH;
        //Random x displacement between -400 and 400
        double yDisplacement = (Math.random()*-Constants.Y_DISPLACEMENT_FACTOR) - Constants.Y_DISPLACEMENT_MIN;
        //Random y displacement between -60 and -160
        double xLoc = previousPlatform.getX() + xDisplacement;
        //Checks whether the platform is on the screen
        while (xLoc <= 0 || xLoc >= Constants.SCREEN_WIDTH - Constants.PLATFORM_WIDTH){
            xDisplacement = (Math.random()* Constants.X_DISPLACEMENT) - Constants.SCREEN_WIDTH;
            //If the platform would be offscreen, a new displacement is made
            xLoc = previousPlatform.getX() + xDisplacement;
        }
        this.platform.setX(previousPlatform.getX() + xDisplacement);//Sets x of platform
        this.platform.setY(previousPlatform.getY() + yDisplacement);//Sets y of platform
    }

    /**
     * Mutator method that sets the color of the rectangle that appears graphically
     */
    public void setColor(Color color) { this.platform.setFill(color); }

    /**
     * Accessor that returns the x location of the rectangle in Platform
     */
    public double getX() { return this.platform.getX(); }

    /**
     * Accessor that returns the Y location of the rectangle in Platform
     */
    public double getY() { return this.platform.getY(); }

    /**
     * Mutator method that allows the x location of the Rectangle within the platform class to be changed.
     */
    public void setX(double x) { this.platform.setX(x);}

    /**
     * Mutator method that allows the y location of the Rectangle within the platform class to be changed.
     */
    public void setY(double y) { this.platform.setY(y);}


    /**
     * Returns whether the platform is a green platform so when the character bounces off of it, it gives a larger
     * jump than normal. (Used in each of the subclasses of platform to differentiate between them)
     */
    public boolean isGreen() { return false; }

    /**
     * Returns whether the platform is a red platform so when the character bounces off of it, the platform disappears
     * logically and graphically. (Used in each of the subclasses of platform to differentiate between them)
     */
    public boolean isRed() { return false; }

    /**
     * This causes red platforms to disappear from the screen, but it needs to be available ot all platforms because
     * the ArrayList is for the platform superclass, so this allows the platforms to disappear, and then there is an
     * override method in the red platform class that causes red platforms to disappear.
     */
    public void disappear(Platform tempPlatform){}

}
