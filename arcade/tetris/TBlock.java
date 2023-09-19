package arcade.tetris;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 *This class contains all the logic for the T piece
 */

public class TBlock extends Piece {


    public TBlock(Rectangle[][] myBoard, ArrayList<Piece> myPieceList, Timeline timeline, Pane gamePane) {
        super(myBoard, myPieceList, timeline, gamePane);
        this.setColor(Color.MEDIUMPURPLE);
        this.setUpCoordinates();
        this.rotate();
    }

    /**
     *This method sets up the coordinates for the T block
     */
    private void setUpCoordinates() {
        int[][] coordinates = new int[Constants.PIECE_START_COORD][Constants.PIECE_START_COORDINATE_1];
        coordinates[0] = new int[]{Constants.PIECE_START_COORD, Constants.PIECE_START_COORDINATE_1};
        coordinates[1] = new int[]{coordinates[0][0]+1,coordinates[0][1]};
        coordinates[2] = new int[]{coordinates[0][0]-1,coordinates[0][1]};
        coordinates[3] = new int[]{coordinates[0][0],coordinates[0][1]+1};
        for (int i = 0; i < coordinates.length; i++) {
            this.setBoard(coordinates[i]);
        }
        this.setCoordinates(coordinates);
    }
    /**
     *This method returns the case for the T block
     */
    @Override
    public int getCase() {
        return 2;
    }
}
