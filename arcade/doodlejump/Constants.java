package arcade.doodlejump;

/**
 * This is your Constants class. It defines some constants you will need
 * in DoodleJump, using the default values from the demo--you shouldn't
 * need to change any of these values unless you want to experiment. Feel
 * free to add more constants to this class!
 *
 * A NOTE ON THE GRAVITY CONSTANT:
 *   Because our y-position is in pixels rather than meters, we'll need our
 *   gravity to be in units of pixels/sec^2 rather than the usual 9.8m/sec^2.
 *   There's not an exact conversion from pixels to meters since different
 *   monitors have varying numbers of pixels per inch, but assuming a fairly
 *   standard 72 pixels per inch, that means that one meter is roughly 2800
 *   pixels. However, a gravity of 2800 pixels/sec2 might feel a bit fast. We
 *   suggest you use a gravity of about 1000 pixels/sec2. Feel free to change
 *   this value, but make sure your game is playable with the value you choose.
 */
public class Constants {

    public static final double GRAVITY = .15; // acceleration constant (UNITS: pixels/frame^2)
    public static final int REBOUND_VELOCITY = -9; // initial jump velocity (UNITS: pixels/frame)
    public static final int BOUNCY_REBOUND_VELOCITY = -13; //bouncy platform initial jump velocity (UNITS: pixels/frame)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)

    public static final int PLATFORM_WIDTH = 40; // (UNITS: pixels)
    public static final int PLATFORM_HEIGHT = 10; // (UNITS: pixels)
    public static final int DOODLE_WIDTH = 20; // (UNITS: pixels)
    public static final int DOODLE_HEIGHT = 40; // (UNITS: pixels)

    public static final int INITIAL_X = 150;
    public static final int INITIAL_Y = 300;
    public static final int RIGHT_BOUND = 400;
    public static final int Y_MIN = 250;

    public static final int BUTTON_WIDTH = 70;
    public static final int BUTTON_HEIGHT = 30;
    public static final int BUTTON_TRANSLATE = -20;
    public static final int LABEL_WIDTH = 400;
    public static final int LABEL_HEIGHT = 0;

    public static final int SCREEN_WIDTH = 400;
    public static final int SCREEN_HEIGHT = 600;

    public static final int X_DISPLACEMENT = 800;
    public static final int Y_DISPLACEMENT_FACTOR = 100;
    public static final int Y_DISPLACEMENT_MIN = 60;

    public static final int GAMEOVER_X = 170;
    public static final int GAMEOVER_Y = 270;

    public static final int PLATFORM_COUNT = 4;
    public static final int MAX_PLATFORMS = 12;

    public static final double SCORE_INCREMENT = 0.1;

    public static final int START_PLATFORM_X = 150;
    public static final int START_PLATFORM_Y = 450;

    public static final int RED_DESPAWN = 1000;
}
