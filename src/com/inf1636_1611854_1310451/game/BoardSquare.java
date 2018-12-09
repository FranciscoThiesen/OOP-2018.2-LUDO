package com.inf1636_1611854_1310451.game;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.inf1636_1611854_1310451.util.Vector2D;

public class BoardSquare {
    public Vector2D topLeftPosition;
    public int sideLength;
    public Color color;
    
    public Vector<Piece> piecesInThisSquare = new Vector<Piece>();
    
    private boolean isTerminalBoardSquare = false;
    private boolean isInitialBoardSquare = false;
    private boolean isShelterBoardSquare = false;

    public BoardSquare() {
        this.topLeftPosition = new Vector2D(0, 0);
        this.sideLength = 10;
    }
    
    public BoardSquare(Vector2D topLeftPosition, int sideLength) {
        this.setPosition(topLeftPosition);
    }
    
    public void addPiece(Piece piece) {
    	this.piecesInThisSquare.add(piece);
    }
    
    public void setAsInitialBoardSquare(Color color) {
    	this.isInitialBoardSquare = true;
    	this.setColor(color);
    }
    
    public void setAsTerminalBoardSquare(Color color) {
    	this.isTerminalBoardSquare = true;
    	this.setColor(color);
    }
    
    public void setAsShelter() {
    	this.isShelterBoardSquare = true;
    }
    
    public boolean isShelter() {
    	return this.isShelterBoardSquare;
    }
    
    public boolean isTerminal() {
    	return this.isTerminalBoardSquare;
    }
    
    public boolean isInitial() {
    	return this.isInitialBoardSquare;
    }
    
    public boolean isFull() {
    	if(this.isTerminal()) {
    		return false;
    	}
    	return this.getPieces().size() >= 2;
    }
    
    public void removePiece(Piece piece) {
    	this.piecesInThisSquare.remove(piece);
    }
    
    public boolean hasPieceOfSameColor() {
		for(Piece piece : this.getPieces()) {
			if(piece.getPlayer().getColor() == this.getColor()) {
				return true;
			}
		}
		return false;
    }
    
    public boolean isCapturable() {
    	if(this.isFull()) return false;
    	if(this.isShelter()) return false;
    	if(this.isInitial() && this.hasPieceOfSameColor()) return false;
    	return true;
    }
    
    public Vector<Piece> getPieces() {
    	return this.piecesInThisSquare;
    }
    
    public Vector<Player> getPlayersWithBarrierInThisSquare() {
    	Vector<Player> playersThatHaveOnePiece = new Vector<Player>();
    	Vector<Player> playersThatHaveTwoPiecesOrMore = new Vector<Player>();
		for(Piece piece : this.getPieces()) {
			Player player = piece.getPlayer();
			if(playersThatHaveTwoPiecesOrMore.contains(player)) {
				// do nothing
			} else if(playersThatHaveOnePiece.contains(player)) {
				playersThatHaveOnePiece.remove(player);
				playersThatHaveTwoPiecesOrMore.add(player);
				continue;
			} else {
				playersThatHaveOnePiece.add(player);
			}
		}
		return playersThatHaveTwoPiecesOrMore;
    }

    public Rectangle2D toRect() {
        Rectangle2D r2D = new Rectangle2D.Float( (float) this.topLeftPosition.x, (float) this.topLeftPosition.y, (float) this.sideLength, (float) this.sideLength);
        return r2D;
    }
    
    public void setPosition(Vector2D topLeftPosition) {
        this.topLeftPosition = topLeftPosition;
    }
    
    public Vector2D getTopLeftPosition() {
    	return this.topLeftPosition;
    }
    
    public void setColor(Color c) {
    	this.color = c;
    }
    
    public Color getColor() {
    	return this.color;
    }
}
