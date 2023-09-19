/**
 * This subclass of the platform is very simple, as it sets the color of the platform to green, and then returns true
 * to the isGreen method so that when the character bounces off of it will get the extra high jump instead of a normal
 * jump. This class is associated with the gamePane so that the platform can be added graphically.
 */
package arcade.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GreenPlatform extends Platform {

    public GreenPlatform(Pane gamePane) {
        super(gamePane);
        this.setColor(Color.GREEN);
    }

    /**
     * Returns whether the platform is a green platform so when the character bounces off of it, it gives a larger
     * jump than normal. (Used in each of the subclasses of platform to differentiate between them)
     */
    @Override
    public boolean isGreen(){
        return true;
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
