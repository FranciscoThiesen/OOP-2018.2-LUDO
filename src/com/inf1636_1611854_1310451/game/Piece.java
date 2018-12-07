package com.inf1636_1611854_1310451.game;
import java.util.*;
import java.math.*;

public class Piece {
	private Player player;
	private int pathIndex;
	private Vector<Integer> pieceTrack;

	public Piece(Player player, Vector<Integer> track) {
		this.player = player;
		this.pathIndex = 0;
		this.pieceTrack = track;
	}

	public int getPathIndex() {
		return this.pathIndex;
	}

	public Vector<Integer> getPieceTrack() {
		return this.pieceTrack;
	}
	
	public int getBoardTrueIndex() {
		return this.pieceTrack.get(this.pathIndex);
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public void setPathIndex(int i) {
		this.pathIndex = i;
	}
	
	public void moveToBoardSquare(Board board, BoardSquare boardSquare) {
		Vector<Integer> pieceTrack = this.getPieceTrack();
		for(int i=0; i<pieceTrack.size(); i++) {
			if(board.squares.get(pieceTrack.get(i)) == boardSquare) {
				this.setPathIndex(i);
			}
		}
	}
	
	public BoardSquare pathIndexToBoardSquare(Board board, int pathIndex) {
		return board.squares.get(this.getPieceTrack().get(pathIndex));
	}

}
