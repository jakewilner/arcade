package arcade.flappybird;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Controls most of the game's logic, contains the timeline
 */

public class Game {

    private ArrayList<Pipe> pipeList;
    private ArrayList<Bird> birdList;
    private Pane gamePane;
    private Timeline timeline;
    private Label scoreLabel;
    private Label highScoreLabel;
    private Label pausedLabel;
    private int score;
    private static int highScore = 0;
    public static boolean gameOver;
    private Boolean paused;

    /**
     * Instantiates and associates various different objects
     */
    public Game(Pane myGamePane, Label myScore, Label myHighScore) {
        this.paused = false;
        this.scoreLabel = myScore;
        this.highScoreLabel = myHighScore;
        this.gameOver = false;
        this.score = 0;
        this.highScoreLabel.setText("High Score: "+this.highScore);
        this.scoreLabel.setText("Score: "+this.score);
        this.pausedLabel = new Label("Paused");
        this.pausedLabel.setLayoutX(Constants.SCENE_WIDTH/2); //intentionally left without constants for readability
        this.pausedLabel.setLayoutY(Constants.SCENE_HEIGHT/2);
        this.pausedLabel.setTextFill(Color.GRAY);
        this.birdList = new ArrayList<>();
        this.pipeList = new ArrayList<>();
        this.gamePane = myGamePane;
        this.pipeList.add(new Pipe(Constants.START_PIPE, this.gamePane));
        this.birdList.add(new Bird(this.gamePane));
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.checkKey(e));
        this.setUpTimeline();
    }

    //sets up the timeline
    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), (ActionEvent e) -> this.runKeyFrame());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * Checks key presses, ensuring proper action occurs with pause and end game conditions
     */
    private void checkKey(KeyEvent e) {
        if (!this.gameOver) {
            if (e.getCode() == KeyCode.SPACE && !this.paused) {
                this.birdList.get(0).jump();
            } else if (e.getCode() == KeyCode.P && this.paused) {
                this.timeline.play();
                this.paused = false;
                this.gamePane.getChildren().remove(this.pausedLabel);
            } else if (e.getCode() == KeyCode.P) {
                this.timeline.pause();
                this.paused = true;
                this.gamePane.getChildren().add(this.pausedLabel);
            }
        }
    }
    /**
     * The method called every keyFrame
     * Moves pipes, bird, checks score, checks boundaries, etc.
     */
    private void runKeyFrame() {
        if (this.pipeList.get(this.pipeList.size()-1).getX() == Constants.PIPE_SPAWN_LOC) {
            double rand = Math.random()*(Constants.SCENE_HEIGHT - Constants.PIPE_LIMIT*2) +
                    Constants.PIPE_LIMIT; //intentionally left without constants for readability
            this.pipeList.add(new Pipe(rand, this.gamePane));
        }
        Pipe removePipe = null;
        for (Pipe pipe : this.pipeList) {
            pipe.move();
            if (pipe.getX() < -Constants.PIPE_WIDTH) { removePipe = pipe; }
            Bounds[] pipeBounds = pipe.getBounds();
            for (Bird bird : this.birdList) {
                if (pipeBounds[0].intersects(bird.getBounds())) {
                    Game.gameOver = true;
                } else if (pipeBounds[1].intersects(bird.getBounds())) {
                    Game.gameOver = true;
                }
            }
            if (Constants.BIRD_X == pipe.getX() + Constants.PIPE_WIDTH) {
                this.score++;
                this.scoreLabel.setText("Score: "+this.score);
                if (this.score > this.highScore) {
                    this.highScore = this.score;
                    this.highScoreLabel.setText("High Score: "+this.highScore);
                }
            }
        }
        for (Bird bird : this.birdList) {
            bird.move();
        }
        this.remove(removePipe); //removes pipe flagged for removal
        if (Game.gameOver) { this.endGame(); } //ends game if condition triggered
    }

    //removes pipe
    private void remove(Pipe pipe) {
        if (pipe != null) {
            this.pipeList.remove(pipe);
            pipe.remove();
            pipe = null;
        }
    }

    //ends game, adds gameOver label
    private void endGame() {
        this.birdList.get(0).remove();
        Label gameOver = new Label("Game Over");
        gameOver.setTextFill(Color.RED);
        gameOver.setLayoutX(Constants.SCENE_WIDTH/2); //intentionally left without constants for readability
        gameOver.setLayoutY(Constants.SCENE_HEIGHT/2);
        this.gamePane.getChildren().add(gameOver);
        this.timeline.stop();
    }

    //resets game, clears lists
    public void reset() {
        this.timeline.stop();
        for (int i = 0; i < this.birdList.size(); i++) { this.birdList.get(i).remove(); }
        for (int i = 0; i < this.pipeList.size(); i++) { this.pipeList.get(i).remove(); }
        this.gamePane.getChildren().removeAll(this.gamePane.getChildren());
        this.score = 0;
        this.scoreLabel.setText("Score: 0");
    }
}