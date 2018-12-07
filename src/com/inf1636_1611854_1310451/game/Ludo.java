package com.inf1636_1611854_1310451.game;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Vector;

import com.inf1636_1611854_1310451.util.Pair;
import com.inf1636_1611854_1310451.util.Subject;
import com.inf1636_1611854_1310451.util.SubjectVoid;

import java.util.ArrayList;
import java.util.Optional;

public class Ludo {

	private Board board = new Board();
	private Die die = new Die();
	private Vector<Player> players = new Vector<Player>();
	private int currentPlayerIndex = 0;
	private Piece selectedPiece;
	
	public Subject<Ludo> onStateChange = new Subject<Ludo>();

	// ===========================================
	// SINGLETON DESIGN PATTERN
	// ===========================================
	
	private static Ludo instance = null;

	public static Ludo getInstance() {
		if(Ludo.instance == null) {
			Ludo.instance = new Ludo();
		}
		return Ludo.instance;
	}
	
	// ===========================================
	// CONSTRUCTOR AND STATE PROPAGATORS
	// ===========================================

	private Ludo() {
		this.players.add(new Player(Color.RED, board.redPiecesTracks, "RED"));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks, "GREEN"));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks, "BLUE"));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks, "YELLOW"));

    	this.die.onStateChange.attach((Die die) -> { this.onDieStateChange(die); });
	}
	
	private void onDieStateChange(Die die) {
		this.onStateChange.notifyAllObservers(this);
	}
	
	// ===========================================
	// GETTERS AND SETTERS
	// ===========================================
	
	private void setSelectedPiece(Piece piece) {
		this.selectedPiece = piece;
		this.onStateChange.notifyAllObservers(this);
	}
	
	public Piece getSelectedPiece() {
		return this.selectedPiece;
	}
	
	private void setCurrentPlayerIndex(int index) {
		this.currentPlayerIndex = index;
		this.onStateChange.notifyAllObservers(this);
	}
	
	private int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	// ===========================================
	// ACCESS METHODS
	// ===========================================
	
	// -------------- private ------------
	private Vector<Piece> getPiecesArray() {
		Vector<Piece> piecesArray = new Vector<Piece>();
		for(Player player: this.players) {
			for(Piece piece: player.getPieces()) {
				piecesArray.add(piece);
			}
		}
		return piecesArray;
	}
	
	//--------------- public -------------
	
	public Vector<BoardSquare> getBoardSquareArray() {
		return this.board.squares;
	}
	
	public Player getCurrentPlayer() {
		return this.players.get(this.getCurrentPlayerIndex());
	}
	
	public boolean hasPieceSelected() {
		return this.selectedPiece != null;
	}

	public boolean isPieceSelected(Piece piece) {
		return this.selectedPiece == piece;
	}
	
	public Die getDie() {
		return this.die;
	}
	
	public Vector<PiecePositioningInfo> getPiecesInformation() {
		Vector<PiecePositioningInfo> piecesInformation = new Vector<PiecePositioningInfo>();
		for(Piece piece: this.getPiecesArray()) {
			PiecePositioningInfo info = new PiecePositioningInfo(piece,
					piece.getCurrentBoardSquare(),
					piece.getPlayer());
			piecesInformation.add(info);
		}
		return piecesInformation;
	}
	
	public Vector<PossiblePieceMovement> getPlacesGivenPieceCanMove(Piece p) {
		Vector<PossiblePieceMovement> vec = new Vector<PossiblePieceMovement>();
		Vector<PossiblePieceMovement> allMoves = this.allMoves(this.getCurrentPlayer());
		for(PossiblePieceMovement move: allMoves) {
			if(move.piece == p) {
				vec.add(move);
			}
		}
		return vec;
	}
	
	// ===========================================
	// STATE CHANGERS
	// ===========================================

	public void unselectPiece() {
		this.setSelectedPiece(null);
	}
	
	public void clickOnPiece(Piece piece) {
		if(piece.getPlayer() == this.players.get(this.getCurrentPlayerIndex())) {
			this.setSelectedPiece(piece);
		}
	}
	
	public void clickBoardSquare(BoardSquare b) {
		for(Piece piece: this.getPiecesArray()) {
			if(piece.getCurrentBoardSquare() == b) {
				this.clickOnPiece(piece);
			}
		}
		if(	this.hasPieceSelected() &&
			this.getSelectedPiece().getPlayer() == this.getCurrentPlayer()) {
			Vector<PossiblePieceMovement> moves = this.getPlacesGivenPieceCanMove(this.getSelectedPiece());
			for(PossiblePieceMovement move: moves) {
				if(move.boardSquare == b && this.isPieceSelected(move.piece)) {
					move.piece.moveToBoardSquare(this.board, move.boardSquare);
					this.die.use();
					this.onStateChange.notifyAllObservers(this);
					this.unselectPiece();
				}
			}
		}
	}
	
	public void endTurn() {
		this.setCurrentPlayerIndex((this.getCurrentPlayerIndex() + 1) % 4); 
	}
	
	public void rollDie() {
		this.die.roll();
	}
	
	// ===========================================

	// Lembrar que na ultima casa nao existe barreira. Podemos ter ate mesmo todos os peoes de uma cor na ultima casa da cor. Peoes com pathIndex = 0 estao no santuario, tambem nao formam barreira
	private boolean playerHasBarrier(Player playerNumber) {
		Vector<Piece> pieces = playerNumber.getPieces();
		for(Piece p1 : pieces) {
			for(Piece p2 : pieces) {
				if(p1 == p2) continue;
				if(p1.getPathIndex() == p2.getPathIndex() && p1.getPathIndex() < board.trackLength - 1 && p1.getPathIndex() > 0) return true;
			}
		}
		return false;
	}

	private Vector<PossiblePieceMovement> allMoves(Player player) {
		Die die = this.getDie();
		Vector<PossiblePieceMovement> allPossibleMoves = new Vector<PossiblePieceMovement>();
		Vector<Piece> pieces = player.getPieces();
		
		if(die.hasBeenUsed()) {
			return new Vector<PossiblePieceMovement>();
		}
		
		if(die.getValue() == 5) {
			Vector<Piece> eligeblePieces = player.getPiecesEligebleToTakeOutOfSanctuary();
			if(eligeblePieces.size() > 0) {
				return PossiblePieceMovement.generateMovementsFromArray(eligeblePieces, 1, die.getValue());
			}
		}
		if(die.getValue() == 6) {
			Vector<Piece> eligeblePieces = player.getBarrierPiecesThatCanMoveAGivenNumber(6);
			if(eligeblePieces.size() > 0) {
				return PossiblePieceMovement.generateMovementsFromArray(eligeblePieces, 1, die.getValue());
			}
		}
		Vector<Piece> eligeblePieces = player.getPiecesThatCanMoveAGivenNumber(die.getValue());
		return PossiblePieceMovement.generateMovementsFromArray(eligeblePieces, die.getValue(), die.getValue());
	}

}
