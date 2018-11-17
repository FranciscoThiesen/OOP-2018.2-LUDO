import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class Board {

    public Vector<BoardSquare> squares;
    
    public Vector<Vector<Integer>> redPiecesTracks;
    public Vector<Vector<Integer>> greenPiecesTracks;
    public Vector<Vector<Integer>> yellowPiecesTracks;
    public Vector<Vector<Integer>> bluePiecesTracks;
    public int trackLength;
    private int squareSideLength = 40;
    
    private Vector<Integer> buildVectorWithIntervalIncluding(int x, int y) {
    	Vector<Integer> v = new Vector<Integer>();
    	for(int i=x; i<=y; i++) {
    		v.add(i);
    	}
    	return v;
    }
    
    private Vector<Integer> buildPieceTrack(	int startingIndex,
    											Vector<Integer> leg0,
    											Vector<Integer> leg1, 
    											Vector<Integer> leg2, 
    											Vector<Integer> leg3,
    											Vector<Integer> finalTrack,
    											int finalSquareIndex) {
    	Vector<Integer> track = new Vector<Integer>();
    	track.add(startingIndex);
    	track.addAll(leg0);
    	track.addAll(leg1);
    	track.addAll(leg2);
    	track.addAll(leg3);
    	track.addAll(finalTrack);
    	track.add(finalSquareIndex);
    	return track;
    }
    
    private void colorVectorWithColor(Vector<Integer> track, Color c) {
    	for(int i=0; i<track.size(); i++) {
    		this.squares.get(track.get(i)).setColor(c);
    	}
    }
    
    private void buildSquaresAndTracks() {
        this.squares = new Vector<BoardSquare>();
        for(int i=0; i<92; i++) {
        	this.squares.add(new BoardSquare());
        }
        Vector<Integer> redLeg = this.buildVectorWithIntervalIncluding(16, 28);
        Vector<Integer> greenLeg = this.buildVectorWithIntervalIncluding(29, 41);
        Vector<Integer> yellowLeg = this.buildVectorWithIntervalIncluding(42, 54);
        Vector<Integer> blueLeg = this.buildVectorWithIntervalIncluding(55, 67);
        
        Vector<Integer> redFinalPart = this.buildVectorWithIntervalIncluding(68, 72);
        Vector<Integer> greenFinalPart = this.buildVectorWithIntervalIncluding(73, 77);
        Vector<Integer> yellowFinalPart = this.buildVectorWithIntervalIncluding(78, 82);
        Vector<Integer> blueFinalPart = this.buildVectorWithIntervalIncluding(83, 87);
        
        this.redPiecesTracks = new Vector<Vector<Integer>>();
        this.greenPiecesTracks = new Vector<Vector<Integer>>();
        this.yellowPiecesTracks = new Vector<Vector<Integer>>();
        this.bluePiecesTracks = new Vector<Vector<Integer>>();

        this.redPiecesTracks.add(this.buildPieceTrack(0, redLeg, greenLeg, yellowLeg, blueLeg, redFinalPart, 88));
        this.redPiecesTracks.add(this.buildPieceTrack(1, redLeg, greenLeg, yellowLeg, blueLeg, redFinalPart, 88));
        this.redPiecesTracks.add(this.buildPieceTrack(2, redLeg, greenLeg, yellowLeg, blueLeg, redFinalPart, 88));
        this.redPiecesTracks.add(this.buildPieceTrack(3, redLeg, greenLeg, yellowLeg, blueLeg, redFinalPart, 88));
        
        this.greenPiecesTracks.add(this.buildPieceTrack(4, greenLeg, yellowLeg, blueLeg, redLeg, greenFinalPart, 89));
        this.greenPiecesTracks.add(this.buildPieceTrack(5, greenLeg, yellowLeg, blueLeg, redLeg, greenFinalPart, 89));
        this.greenPiecesTracks.add(this.buildPieceTrack(6, greenLeg, yellowLeg, blueLeg, redLeg, greenFinalPart, 89));
        this.greenPiecesTracks.add(this.buildPieceTrack(7, greenLeg, yellowLeg, blueLeg, redLeg, greenFinalPart, 89));
        
        this.yellowPiecesTracks.add(this.buildPieceTrack(12, yellowLeg, blueLeg, redLeg, greenLeg, yellowFinalPart, 90));
        this.yellowPiecesTracks.add(this.buildPieceTrack(13, yellowLeg, blueLeg, redLeg, greenLeg, yellowFinalPart, 90));
        this.yellowPiecesTracks.add(this.buildPieceTrack(14, yellowLeg, blueLeg, redLeg, greenLeg, yellowFinalPart, 90));
        this.yellowPiecesTracks.add(this.buildPieceTrack(15, yellowLeg, blueLeg, redLeg, greenLeg, yellowFinalPart, 90));
        
        this.bluePiecesTracks.add(this.buildPieceTrack(8, blueLeg, redLeg, greenLeg, yellowLeg, blueFinalPart, 91));
        this.bluePiecesTracks.add(this.buildPieceTrack(9, blueLeg, redLeg, greenLeg, yellowLeg, blueFinalPart, 91));
        this.bluePiecesTracks.add(this.buildPieceTrack(10, blueLeg, redLeg, greenLeg, yellowLeg, blueFinalPart, 91));
        this.bluePiecesTracks.add(this.buildPieceTrack(11, blueLeg, redLeg, greenLeg, yellowLeg, blueFinalPart, 91));
        
        int[][] boardPositions = {	{-1, -1, -1, -1, -1, -1, 26, 27, 28, -1, -1, -1, -1, -1, -1},
        							{-1,  0, -1, -1,  1, -1, 25, 73, 29, -1,  4, -1, -1,  5, -1},
        							{-1, -1, -1, -1, -1, -1, 24, 74, 30, -1, -1, -1, -1, -1, -1},
        							{-1, -1, -1, -1, -1, -1, 23, 75, 31, -1, -1, -1, -1, -1, -1},
        							{-1,  2, -1, -1,  3, -1, 22, 76, 32, -1,  6, -1, -1,  7, -1},
        							{-1, -1, -1, -1, -1, -1, 21, 77, 33, -1, -1, -1, -1, -1, -1},
        							{67, 16, 17, 18, 19, 20, -1, 89, -1, 34, 35, 36, 37, 38, 39},
        							{66, 68, 69, 70, 71, 72, 88, -1, 90, 82, 81, 80, 79, 78, 40},
        							{65, 64, 63, 62, 61, 60, -1, 91, -1, 46, 45, 44, 43, 42, 41},
        							{-1, -1, -1, -1, -1, -1, 59, 87, 47, -1, -1, -1, -1, -1, -1},
        							{-1,  8, -1, -1,  9, -1, 58, 86, 48, -1, 12, -1, -1, 13, -1},
        							{-1, -1, -1, -1, -1, -1, 57, 85, 49, -1, -1, -1, -1, -1, -1},
        							{-1, -1, -1, -1, -1, -1, 56, 84, 50, -1, -1, -1, -1, -1, -1},
        							{-1, 10, -1, -1, 11, -1, 55, 83, 51, -1, 14, -1, -1, 15, -1},
        							{-1, -1, -1, -1, -1, -1, 54, 53, 52, -1, -1, -1, -1, -1, -1}};
        
        
        for(int y=0; y<15; y++) {
        	for(int x=0; x<15; x++) {
        		if(boardPositions[y][x] != -1) {
            		this.squares.get(boardPositions[y][x]).setPosition(new Vector2D(x * squareSideLength, y * squareSideLength));
            		this.squares.get(boardPositions[y][x]).setSideLength(this.squareSideLength);
        		}
        	}
        }
        
        for(int i=0; i<92; i++) {
        	this.squares.get(i).setColor(Color.WHITE);
        }

        // Every track has the same length, I have just chosen a random one
        this.trackLength = this.bluePiecesTracks.get(0).size();

        this.colorVectorWithColor(redFinalPart, Color.RED);
        this.colorVectorWithColor(greenFinalPart, Color.GREEN);
        this.colorVectorWithColor(yellowFinalPart, Color.YELLOW);
        this.colorVectorWithColor(blueFinalPart, Color.BLUE);

        this.squares.get(redLeg.get(0)).setColor(Color.RED);
        this.squares.get(greenLeg.get(0)).setColor(Color.GREEN);
        this.squares.get(yellowLeg.get(0)).setColor(Color.YELLOW);
        this.squares.get(blueLeg.get(0)).setColor(Color.BLUE);
        
        this.squares.get(88).setColor(Color.RED);
        this.squares.get(89).setColor(Color.GREEN);
        this.squares.get(90).setColor(Color.YELLOW);
        this.squares.get(91).setColor(Color.BLUE);
    }

    public Board() {
    	this.buildSquaresAndTracks();
    }

}
