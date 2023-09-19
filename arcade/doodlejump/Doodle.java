/**
 * The doodle class is the top level logic class, it includes the timeline and the methods that are put into a collective
 * keyframe and then placed into the timeline. This class also deals with the ArrayList of platforms, including spawning
 * and despawning the platforms, and moving the collective. The labels that change with time (Score and GameOver) are
 * also controlled based on this class and are updated or shown graphically based on the timeline. This class contains
 * an instance of a Timeline, a keyframe, a character, and multiple instances of platforms. There is an association
 * between this class and the gamePane and the scoreLabel so that they can be updated within this class and displayed
 * within the PaneOrganizer class.
 */
package arcade.doodlejump;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Doodle {

    private Timeline timeline;
    private ArrayList<Platform> platformList;
    private Character character;
    private double vectorY;
    private double vectorX;
    private double score;
    private Pane gamePane;
    private Label scoreLabel;
    public static Boolean gameOver;
    public static Boolean paused = false;

    public Doodle(Pane myGamePane, Label myScoreLabel) {
        this.gameOver = false;
        this.timeline = new Timeline();
        this.gamePane = myGamePane;
        this.scoreLabel = myScoreLabel;
        this.platformList = new ArrayList<>();
        this.character = new Character(this.gamePane, this);
        this.vectorY = 0;
        this.vectorX = 0;
        this.score = 0;
        this.setUpTimeline();
        Platform startPlatform = new BlackPlatform(this.gamePane);
        startPlatform.setX(Constants.START_PLATFORM_X);
        startPlatform.setY(Constants.START_PLATFORM_Y);
        this.platformList.add(startPlatform);
        this.setUpAction();
    }

    private void setUpAction() {
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.character.keyPress(e));
        this.gamePane.setOnKeyReleased((KeyEvent e) -> this.character.keyRelease(e));
    }
    /**
     * Method that adds keyframes to the timeline and adds them so they run indefinately. The keyframes include
     * the movement of the character, the spawning of platforms, the despawning of platforms, the score label
     * updater, the key presses for the characters, and the game over detection.
     */
    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), (ActionEvent e) -> this.setupKeyFrame());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * Private helper method that combines all of the different things that need to update with the timeline within
     * a single keyframe (they are all occuring at the same time on the same timeline, this just saves lines of code
     * in the future)
     */
    private void setupKeyFrame(){
        if (!this.paused) {
            this.setMovement();
            this.spawnPlatforms();
            this.despawnPlatform();
            this.updateLabel();
            this.gameOver();
        }
    }

    /**
     * Checks if the character should move or if the platform should move, and moves the correct object based
     * on the result. It also increments the vectorY value by the gravity constant for the realistic acceleration
     * physics.
     */
    private void setMovement() {
            this.vectorY += Constants.GRAVITY;
            //check for collision, set vectors
            if (this.character.charShouldMove(this.vectorY)) { moveCharacter(); }
            else { movePlatform(); }
    }

    /**
     * Moves the character horizonitally by the value vectorX and vertically by the value vectorY
     */
    private void moveCharacter() {
        this.character.move(this.vectorX, this.vectorY);
    }

    /**
     * Moves each of the platforms in the platformList down by a quanity decided by vectorY, and increments the
     * score based on how long the platforms are moving for (the more the platforms move the higher the score is)
     */
    private void movePlatform() {
        this.character.move(this.vectorX, 0);//Keeps the character still in the center of the screen
        for (Platform platform : this.platformList) {
            platform.move(-this.vectorY);
            this.score += Constants.SCORE_INCREMENT;//Adds to the score only when the platforms move up
        }
    }

    /**
     * Method that constantly checks the number of platforms on the screen, and spawns a new platform in when the
     * number of platforms is less than the decided amount. When a platform is spawned it is randomly assigned a
     * color, each with a different property. The platforms are added both graphically and logically within this \
     * method and this method is run through the timeline so that platforms spawn indefinately.
     */
    private void spawnPlatforms(){
        int rand = (int) (Math.random()* Constants.PLATFORM_COUNT); //Makes a random number from 0-3 to make the platforms random
        Platform platform;
        if (this.platformList.size() < Constants.MAX_PLATFORMS){//Checks if there are too few platforms before making another
            switch(rand){
                case 0:
                    platform = new BlackPlatform(this.gamePane);
                    break;
                case 1:
                    platform = new BluePlatform(this.gamePane, this);
                    break;
                case 2:
                    platform = new GreenPlatform(this.gamePane);
                    break;
                default:
                    platform = new RedPlatform(this.gamePane, this);
                    break;
            }
            this.platformList.add(platform); //Adds the platforms graphically and logically
            //Randomized the x and y location of the platforms when they are spawned.
            platform.setDisplacement(this.platformList.get(this.platformList.indexOf(platform) - 1));
        }
    }

    /**
     * Removes the platforms from the graphically and logically when they go offscreen, allowing for a new platform
     * to be spawned on the top of the screen.
     */
    public void despawnPlatform(){
        ArrayList<Platform> removeList = new ArrayList<>();//Used to keep track of the removed platforms
        for(Platform platform: this.platformList){
            if(platform.getY() > Constants.SCREEN_HEIGHT){
                removeList.add(platform);//you can't remove platforms in a for loop
            }
        }
        this.platformList.removeAll(removeList);//Removes the platforms from the screen
        for(Platform platform: removeList){
            platform = null;
        }
    }

    /**
     * Updates the score shown on the label by using the score instance variable (which updates with the movement of
     * the platforms) and displays it as the timeline updates. (this method is used in the timeline)
     */
    public void updateLabel() {
        this.scoreLabel.setText("Score: " + (int)this.score);
        //The score is casted as an int because it is originally a double to not cause the score to increase by too much
        //at once
    }

    /**
     * Responsible for displaying the game over label on the screen when the character goes offscreen, as well as
     * stopping the timeline so that the blue platforms will stop moving after the game ends. This method is used in
     * the timeline so the game is constantly checking if the game has ended.
     */
    public void gameOver(){
        if (this.character.getY() >= Constants.SCREEN_HEIGHT){//Detects if the character is offscreen
            this.gameOver = true;
            Label label = new Label("Game Over");
            this.gamePane.getChildren().add(label);
            label.setAlignment(Pos.CENTER);
            label.setTranslateX(Constants.GAMEOVER_X);
            label.setTranslateY(Constants.GAMEOVER_Y);
            this.timeline.stop();//Stops everything from moving so the game can show that it has ended.
        }
    }

    /**
     * Accessor method for the timeline so that the Blue Platforms can access it and use it when they move back
     * and forth. Returns the one timeline that is used within the game.
     */
    public Timeline getTimeline(){
        return this.timeline;
    }

    /**
     * Accessor method for the platform list so the platform class can access the platforms within the platformList
     */
    public ArrayList<Platform> getPlatformList(){ return this.platformList; }

    /**
     * Mutator for VectorY so the character class can minipulate it's movement
     */
    public void setVectorY(double y) { this.vectorY = y; }

    /**
     * Accessor method for VectorY so that the character class can access the variable.
     */
    public double getVectorY() { return this.vectorY; }

    public void reset() {
        this.gamePane.getChildren().removeAll(this.gamePane.getChildren());
        this.timeline.stop();
    }
}
