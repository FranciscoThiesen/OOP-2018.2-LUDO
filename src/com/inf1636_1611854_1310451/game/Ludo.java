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
	private boolean _isEndTurnActionPossible = false;
	private boolean _isRollActionPossible = false;
	
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
	
	public boolean isEndTurnActionPossible() {
		return this._isEndTurnActionPossible;
	}
	
	private void setIsEndTurnActionPossible(boolean value) {
		this._isEndTurnActionPossible = value;
		this.onStateChange.notifyAllObservers(this);
	}
	
	public boolean isRollActionPossible() {
		return this._isRollActionPossible;
	}
	
	private void setIsRollActionPossible(boolean value) {
		this._isRollActionPossible = value;
		this.onStateChange.notifyAllObservers(this);
	}
	
	public Vector<Player> getPlayers() {
		return this.players;
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
	
	private void executeMove(PossiblePieceMovement move) {
		move.piece.moveToBoardSquare(move.boardSquare);
		this.die.use();
		this.unselectPiece();
		if(die.getValue() == 6) {
			this.setIsRollActionPossible(true);
		} else {
			this.setIsEndTurnActionPossible(true);
		}
	}
	
	public void clickBoardSquare(BoardSquare b) {
		// if does not have a piece selected, select it
		if(!this.hasPieceSelected()) {
			for(Piece piece: b.getPieces()) {
				if(piece.getPlayer() == this.getCurrentPlayer()) {
					this.setSelectedPiece(piece);
				}
			}
			return;
		}
		
		Vector<PossiblePieceMovement> moves = this.getPlacesGivenPieceCanMove(this.getSelectedPiece());
		
		// prioritize movement
		for(PossiblePieceMovement move: moves) {
			if(move.boardSquare == b && this.isPieceSelected(move.piece)) {
				this.executeMove(move);
				return;
			}
		}
		
		// if clicking on the same board, unselect piece
		if(this.getSelectedPiece().getCurrentBoardSquare() == b) {
			this.unselectPiece();
			return;
		}
		
		// last, you can click on other piece to change piece selected
		for(Piece piece: b.getPieces()) {
			if(piece.getPlayer() == this.getCurrentPlayer()) {
				this.setSelectedPiece(piece);
			}
		}
		return;
		
	}
	
	public void endTurn() {
		this.setCurrentPlayerIndex((this.getCurrentPlayerIndex() + 1) % 4);
		this.setIsRollActionPossible(true);
		this.setIsEndTurnActionPossible(false);
	}
	
	public void rollDie() {
		if(this.isRollActionPossible()) {
			this.setIsRollActionPossible(false);
			this.die.roll();
			Vector<PossiblePieceMovement> allPossibleMoves = this.allMoves(this.getCurrentPlayer());
			if(allPossibleMoves.size() == 0) {
				this.setIsEndTurnActionPossible(true);
			}
		}
		
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
				return PossiblePieceMovement.generateMovementsFromArray(eligeblePieces, die.getValue(), die.getValue());
			}
		}
		Vector<Piece> eligeblePieces = player.getPiecesThatCanMoveAGivenNumber(die.getValue());
		return PossiblePieceMovement.generateMovementsFromArray(eligeblePieces, die.getValue(), die.getValue());
	}
	
	public void startGame() {
		for(Player player: this.getPlayers()) {
			Vector<Piece> pieces = player.getPieces();
			Piece firstPiece = pieces.get(0);
			firstPiece.moveToBoardSquare(firstPiece.getBoardSquareInNumMoves(1));
		}
		this.setIsRollActionPossible(true);
	}

}
