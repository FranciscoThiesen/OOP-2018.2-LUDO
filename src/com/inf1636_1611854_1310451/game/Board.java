package com.inf1636_1611854_1310451.game;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.inf1636_1611854_1310451.util.Vector2D;

import java.util.*;

public class Board {

    public Vector<BoardSquare> squares;
    
    public Vector<Vector<BoardSquare>> redPiecesTracks;
    public Vector<Vector<BoardSquare>> greenPiecesTracks;
    public Vector<Vector<BoardSquare>> yellowPiecesTracks;
    public Vector<Vector<BoardSquare>> bluePiecesTracks;
    public int trackLength;
    private int squareSideLength = 40;
    
    private Vector<BoardSquare> buildVectorWithIntervalIncluding(int x, int y) {
    	Vector<BoardSquare> v = new Vector<BoardSquare>();
    	for(int i=x; i<=y; i++) {
    		v.add(this.squares.get(i));
    	}
    	return v;
    }
    
    private Vector<BoardSquare> buildPieceTrack(	int startingIndex,
    											Vector<BoardSquare> leg0,
    											Vector<BoardSquare> leg1, 
    											Vector<BoardSquare> leg2, 
    											Vector<BoardSquare> leg3,
    											Vector<BoardSquare> finalTrack,
    											int finalSquareIndex) {
    	Vector<BoardSquare> track = new Vector<BoardSquare>();
    	track.add(this.squares.get(startingIndex));
    	track.addAll(leg0);
    	track.addAll(leg1);
    	track.addAll(leg2);
    	track.addAll(leg3);
    	track.addAll(finalTrack);
    	track.add(this.squares.get(finalSquareIndex));
    	return track;
    }
    
    private void colorVectorWithColor(Vector<BoardSquare> track, Color c) {
    	for(int i=0; i<track.size(); i++) {
    		track.get(i).setColor(c);
    	}
    }
    
    private void buildSquaresAndTracks() {
        this.squares = new Vector<BoardSquare>();
        for(int i=0; i<92; i++) {
        	this.squares.add(new BoardSquare());
        }
        Vector<BoardSquare> redLeg = this.buildVectorWithIntervalIncluding(16, 28);
        Vector<BoardSquare> greenLeg = this.buildVectorWithIntervalIncluding(29, 41);
        Vector<BoardSquare> yellowLeg = this.buildVectorWithIntervalIncluding(42, 54);
        Vector<BoardSquare> blueLeg = this.buildVectorWithIntervalIncluding(55, 67);
        
        Vector<BoardSquare> redFinalPart = this.buildVectorWithIntervalIncluding(68, 72);
        Vector<BoardSquare> greenFinalPart = this.buildVectorWithIntervalIncluding(73, 77);
        Vector<BoardSquare> yellowFinalPart = this.buildVectorWithIntervalIncluding(78, 82);
        Vector<BoardSquare> blueFinalPart = this.buildVectorWithIntervalIncluding(83, 87);
        
        this.redPiecesTracks = new Vector<Vector<BoardSquare>>();
        this.greenPiecesTracks = new Vector<Vector<BoardSquare>>();
        this.yellowPiecesTracks = new Vector<Vector<BoardSquare>>();
        this.bluePiecesTracks = new Vector<Vector<BoardSquare>>();

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

        redLeg.get(0).setAsInitialBoardSquare(Color.RED);
        greenLeg.get(0).setAsInitialBoardSquare(Color.GREEN);
        yellowLeg.get(0).setAsInitialBoardSquare(Color.YELLOW);
        blueLeg.get(0).setAsInitialBoardSquare(Color.BLUE);
        
        this.squares.get(88).setAsTerminalBoardSquare(Color.RED);
        this.squares.get(89).setAsTerminalBoardSquare(Color.GREEN);
        this.squares.get(90).setAsTerminalBoardSquare(Color.YELLOW);
        this.squares.get(91).setAsTerminalBoardSquare(Color.BLUE);

        this.squares.get(25).setAsShelter();
        this.squares.get(38).setAsShelter();
        this.squares.get(51).setAsShelter();
        this.squares.get(64).setAsShelter();
    }

    public Board() {
    	this.buildSquaresAndTracks();
    }

}
