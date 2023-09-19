/**
 * This subclass of the platform is very simple, as it sets the color of the platform to red, and then returns true
 * to the isRed method so that when the character bounces off of it the platform will be removed both graphically and
 * logically. This class is associated with the gamePane so that the platform can be added graphically.
 */
package arcade.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class RedPlatform extends Platform {

    private Doodle doodle;
    private Pane gamePane;

    public RedPlatform(Pane gamePane, Doodle newDoodle) {
        super(gamePane);
        this.gamePane = gamePane;
        this.setColor(Color.RED);
        this.doodle = newDoodle;
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
        return true;
    }

    /**
     * A method that removes the platform both logically and graphically from the screen. This is used in the doodle
     * class to remove the red platforms when they are bounced upon. They are removed from the screen by being places
     * offscreen and then the despawn method will despawn them.
     */
    @Override
    public void disappear(Platform tempPlatform){
        int index = this.doodle.getPlatformList().indexOf(tempPlatform);
        this.gamePane.getChildren().remove(this.doodle.getPlatformList().get(index));
        this.doodle.getPlatformList().remove(index);
        this.gamePane.getChildren().remove(tempPlatform);
        tempPlatform.setY(Constants.RED_DESPAWN);
        this.doodle.getPlatformList().remove(tempPlatform);
        tempPlatform = null;
    }
}
