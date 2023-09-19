package arcade.evolution;

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
 * Controls the majority of the program's logic
 * Contains timeline
 */

public class Game {

    private ArrayList<Pipe> pipeList;
    private Bird[] birdList;
    private ArrayList<Bird> bestList;
    //easier to use than an array in this case, helps with keeping track of the best birds
    private Pane gamePane;
    private Pane labelPane;
    private Timeline timeline;
    private Label scoreLabel;
    private Label birdsLabel;
    private Label highScoreLabel;
    private Label currentFitness;
    private Label generationLabel;
    private Label averageFitnessLabel;
    private Label bestFitnessLabel;
    private Label pausedLabel;
    private Label gameOverLabel;
    private int score;
    private int highScore;
    public static int birdsAlive;
    private Boolean paused;
    private static Boolean gameOver = false;
    private static double duration = Constants.STANDARD;
    private static int generation = 0;

    //associates gamePane and labelPane, sets up counters and booleans
    public Game(Pane myGamePane, Pane myLabelPane) {
        this.labelPane = myLabelPane;
        this.paused = false;
        this.gamePane = myGamePane;
        Game.birdsAlive = 0;
        this.setUp();
    }

    //continues setup and instantiation
    private void setUp() {
        this.highScore = 0;
        this.score = 0;
        this.bestList = new ArrayList<>();
        this.highScoreLabel = new Label("High score: "+this.score);
        this.scoreLabel = new Label("Score: "+this.score);
        this.birdsLabel = new Label("Alive: "+ Game.birdsAlive);
        this.currentFitness = new Label("Current Fitness: "+ Bird.fitness);
        this.averageFitnessLabel = new Label("Previous Average Fitness: 0");
        this.bestFitnessLabel = new Label("Previous Best Fitness: 0");
        this.generationLabel = new Label("Generation: "+ Game.generation);
        this.scoreLabel.setText("Score: "+this.score);
        this.pausedLabel = new Label("Paused");
        this.pausedLabel.setLayoutX(Constants.SCENE_WIDTH/2); //intentionally left without constants for readability
        this.pausedLabel.setLayoutY(Constants.SCENE_HEIGHT/2);
        this.pausedLabel.setTextFill(Color.GRAY);
        this.gameOverLabel = new Label("Paused");
        this.gameOverLabel.setLayoutX(Constants.SCENE_WIDTH/2); //intentionally left without constants for readability
        this.gameOverLabel.setLayoutY(Constants.SCENE_HEIGHT/2);
        this.gameOverLabel.setTextFill(Color.RED);
        this.labelPane.getChildren().addAll(this.birdsLabel, this.scoreLabel, this.highScoreLabel,
                this.generationLabel, this.averageFitnessLabel, this.bestFitnessLabel, this.currentFitness);
        this.birdList = new Bird[Constants.GENERATION_SIZE+1];
        this.pipeList = new ArrayList<>();
        this.pipeList.add(new Pipe(Math.random()*(Constants.SCENE_HEIGHT - Constants.PIPE_LIMIT*2) +
                Constants.PIPE_LIMIT, this.gamePane)); //intentionally left without constants, readability/clarity
        this.setBirds();
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.checkKey(e));
        this.setUpTimeline(Game.duration);
    }

    /**
     * This method prepares the birds
     * Depending on if they've made it through the first pipe, or not, they're instantiated differently
     * Passes in weights to the birds
     */
    private void setBirds() {
        Game.generation++;
        if (this.highScore == 0) { //if birds fail to get one pipe, re-generate randomly
            for (int k = 0; k < Constants.GENERATION_SIZE+1; k++) {
                double[][] syn0 = new double[Constants.WEIGHT_DIM1][Constants.WEIGHT_DIM2];
                double[] syn1 = new double[Constants.WEIGHT_DIM1];
                for (int i = 0; i < syn0.length; i++) {
                    for (int j = 0; j < syn0[i].length; j++) {
                        syn0[i][j] = Math.random()*Constants.INITAL_MUTATION-1; //intentionally left without constants, readability
                    }
                }
                for (int i = 0; i < syn1.length; i++) {
                    syn1[i] = Math.random();
                }
                this.birdList[k] = new Bird(this.gamePane, syn0, syn1, this.bestList);
            }
        } else {
            for (int h = 0; h < this.bestList.size(); h++) {
                for (int k = 0; k < Constants.GENERATION_SIZE/Constants.WEIGHT_DIM2; k++) {
                    //intentionally left without constant for readability/understanding
                    double[][] syn0 = new double[Constants.WEIGHT_DIM1][Constants.WEIGHT_DIM2];
                    double[] syn1 = new double[Constants.WEIGHT_DIM1];
                    for (int i = 0; i < syn0.length; i++) {
                        for (int j = 0; j < syn0[i].length; j++) {
                            syn0[i][j] = this.bestList.get(h).getSyn0(i,j) +
                                    Math.random()*Constants.MAX_MUTATION-Constants.MIN_MUTATION;
                        }
                    }
                    for (int i = 0; i < syn1.length; i++) {
                        syn1[i] = this.bestList.get(h).getSyn1(i) +
                                Math.random()*Constants.MAX_MUTATION-Constants.MIN_MUTATION;
                    }
                    this.birdList[k+(h)* Constants.GENERATION_SIZE/Constants.WEIGHT_DIM2] =
                            new Bird(this.gamePane, syn0, syn1, this.bestList);
                }
            }
            double[][] syn0 = new double[Constants.WEIGHT_DIM1][Constants.WEIGHT_DIM2];
            double[] syn1 = new double[Constants.WEIGHT_DIM1];
            for (int i = 0; i < syn0.length; i++) {
                for (int j = 0; j < syn0[i].length; j++) {
                    syn0[i][j] = this.bestList.get(0).getSyn0(i,j);
                }
            }
            for (int i = 0; i < syn1.length; i++) {
                syn1[i] = this.bestList.get(0).getSyn1(i);
            }
            this.birdList[Constants.GENERATION_SIZE] = new Bird(this.gamePane, syn0, syn1, this.bestList);
            //keeps the very best bird from last generation
        }
    }

    /**
     * Sets up timeline with a given value, passed in
     */
    public void setUpTimeline(double duration) {
        Game.duration = duration;
        if (this.timeline != null) { this.timeline.stop(); }
        KeyFrame kf = new KeyFrame(Duration.seconds(duration), (ActionEvent e) -> this.runKeyFrame());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
        this.gamePane.requestFocus();
    }

    //checks keyEvents, passed in from PaneOrganizer
    private void checkKey(KeyEvent e) {
        if (!this.gameOver) {
            if (e.getCode() == KeyCode.P && this.paused) {
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
     * Moves pipes, changes fitness, sets input values, and passes them to birds
     */
    private void runKeyFrame() {
        if (!this.gameOver) {
            Bird.fitness++;
            movePipes();
            double[] input = new double[Constants.WEIGHT_DIM2];
            if (this.pipeList.get(0).getX() + Constants.PIPE_WIDTH + Constants.BIRD_RADIUS > Constants.BIRD_X) {
                input[0] = this.pipeList.get(0).getX() / Constants.SCENE_WIDTH;
                input[1] = this.pipeList.get(0).getCenter() / Constants.SCENE_HEIGHT;
            } else {
                input[0] = this.pipeList.get(1).getX() / Constants.SCENE_WIDTH;
                input[1] = this.pipeList.get(1).getCenter() / Constants.SCENE_HEIGHT;
            }
            for (Bird bird : this.birdList) {
                bird.move(input);
            }
            if (Game.birdsAlive <= 0) {
                this.reset();
            }
            this.birdsLabel.setText("Alive: " + Game.birdsAlive);
            this.currentFitness.setText("Current Fitness: " + Bird.fitness);
            this.generationLabel.setText("Generation: " + Game.generation);
            if (this.score > Constants.MAX_SCORE) { //end condition, just in case they're really smart
                this.gamePane.getChildren().add(this.gameOverLabel);
                this.gameOver = true;
            }
        }
    }

    /**
     * Method for pipe movement
     * Called every keyFrame
     */
    private void movePipes() {
        if (this.pipeList.get(this.pipeList.size()-1).getX() == Constants.PIPE_SPAWN_LOC) {
            double rand = Math.random()*(Constants.SCENE_HEIGHT - Constants.PIPE_LIMIT*2) +
                    Constants.PIPE_LIMIT;
            this.pipeList.add(new Pipe(rand, this.gamePane));
        }
        Pipe removePipe = null;
        for (Pipe pipe : this.pipeList) {
            pipe.move();
            if (pipe.getX() < -Constants.PIPE_WIDTH) { removePipe = pipe; }
            Bounds[] pipeBounds = pipe.getBounds();
            for (Bird bird : this.birdList) {
                if (pipeBounds[0].intersects(bird.getBounds())) {
                    bird.die();
                } else if (pipeBounds[1].intersects(bird.getBounds())) {
                    bird.die();
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
        this.remove(removePipe);
    }

    //removes pipes
    private void remove(Pipe pipe) {
        if (pipe != null) {
            this.pipeList.remove(pipe);
            pipe.remove();
            pipe = null;
        }
    }

    /**
     * Resets the entire game
     */
    public void reset() {
        this.timeline.pause();
        int totalFitness = 0;
        this.bestFitnessLabel.setText("Previous Best Fitness: "+
                this.bestList.get(this.bestList.size()-1).getFitness());
        for (int i = 0; i < this.birdList.length; i++) {
            totalFitness += this.birdList[i].getFitness();
            this.birdList[i].remove();
        }
        totalFitness /= this.birdList.length;
        for (int i = 0; i < this.pipeList.size(); i++) {
            this.pipeList.get(i).remove();
            this.pipeList.remove(i);
            i--;
        }
        this.averageFitnessLabel.setText("Previous Average Fitness: "+totalFitness);
        Game.birdsAlive = 0;
        this.score = 0;
        this.scoreLabel.setText("Score: 0");
        this.setBirds();
        this.pipeList.add(new Pipe(Math.random()*(Constants.SCENE_HEIGHT - Constants.PIPE_LIMIT*2) +
                Constants.PIPE_LIMIT, this.gamePane));
        this.gamePane.requestFocus();
        this.timeline.play();
    }
}