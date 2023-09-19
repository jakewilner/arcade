/**
 * This subclass of the platform class is the most vanilla of all of the subclasses, as it just creates a black platform
 * that simply does nothing but allows the character to jump off of it. It is associated with the gamePane so that the
 * platform can be added graphically.
 */
package arcade.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlackPlatform extends Platform {

    public BlackPlatform(Pane gamePane) {
        super(gamePane);
        this.setColor(Color.BLACK);
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
