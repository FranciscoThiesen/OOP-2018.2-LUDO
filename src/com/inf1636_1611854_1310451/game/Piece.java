package com.inf1636_1611854_1310451.game;
import java.util.*;
import java.awt.Color;
import java.math.*;

public class Piece {
	private Player player;
	private int pathIndex;
	private Vector<BoardSquare> pieceTrack;

	public Piece(Player player, Vector<BoardSquare> track) {
		this.player = player;
		this.pieceTrack = track;
		this.setPathIndex(0);
		this.getCurrentBoardSquare().addPiece(this);
	}

	public int getPathIndex() {
		return this.pathIndex;
	}

	public Vector<BoardSquare> getPieceTrack() {
		return this.pieceTrack;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPathIndex(int i) {
		this.pathIndex = i;
	}
	
	public BoardSquare getBoardSquareInNumMoves(int numMoves) {
		return this.getPieceTrack().get(this.getPathIndex() + numMoves);
	}
	
	public BoardSquare getCurrentBoardSquare() {
		return this.getBoardSquareInNumMoves(0);
	}
	
	public void moveToBoardSquare(BoardSquare boardSquare) {
		Vector<BoardSquare> pieceTrack = this.getPieceTrack();
		for(int i=0; i<pieceTrack.size(); i++) {
			if(pieceTrack.get(i) == boardSquare) {
				this.getCurrentBoardSquare().removePiece(this);
				this.setPathIndex(i);
				this.getCurrentBoardSquare().addPiece(this);
			}
		}
	}
	
	public BoardSquare pathIndexToBoardSquare(Board board, int pathIndex) {
		return this.getPieceTrack().get(pathIndex);
	}
	
	private boolean isMovementOutsideBoard(int numMovements) {
		return this.getPathIndex() + numMovements >= this.getPieceTrack().size();
	}
	
	private Vector<BoardSquare> getBoardSquaresInPath(int numMovements) {
		Vector<BoardSquare> boardSquaresInPath = new Vector<BoardSquare>();
		for(int i = 1; i < numMovements; ++i ) {
			boardSquaresInPath.add(this.getBoardSquareInNumMoves(i));
		}
		return boardSquaresInPath;
	}
	
	private boolean isThereABarrierInPath(int numMovements) {
		Player player = this.getPlayer();
		Vector<BoardSquare> boardSquaresInPath = this.getBoardSquaresInPath(numMovements);
		for(BoardSquare boardSquare: boardSquaresInPath) {
			Vector<Player> playersWithBarrierInThisSquare = boardSquare.getPlayersWithBarrierInThisSquare();
			for(Player playerWithBarrier: playersWithBarrierInThisSquare) {
				if(playerWithBarrier != player) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidMove(int numMovements) {
		if(this.isMovementOutsideBoard(numMovements)) return false; // Iria para fora do tabuleiro
		if(this.isThereABarrierInPath(numMovements)) return false;
		
		BoardSquare targetBoardSquare = this.getBoardSquareInNumMoves(numMovements);
		if(targetBoardSquare.isFull()) return false;

		return true;
	}
	
	// If a move the piece to the target position, will it make a capture?
	public boolean makesCapture(BoardSquare targetBoardSquare) {
		if(!targetBoardSquare.isCapturable()) return false;
		for(Piece piece: targetBoardSquare.getPieces()) {
			if(piece.getPlayer() != this.getPlayer()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInSanctuary() {
		return this.getPathIndex() == 0;
	}
	
	public boolean isEligebleToExitSanctuary() {
		if( this.isInSanctuary() ) {
			BoardSquare nxt = this.getBoardSquareInNumMoves(1);
			if(nxt.hasPieceOfSameColor() == true) return false;
			return this.isValidMove(1);
		}
		return false;
	}
	
	public boolean isPartOfABarrier() {
		Vector<Player> playersWithBarrierInThisSquare = this.getCurrentBoardSquare().getPlayersWithBarrierInThisSquare();
		for(Player player: playersWithBarrierInThisSquare) {
			if(this.getPlayer() == player) {
				return true;
			}
		}
		return false;
	}
	
}
