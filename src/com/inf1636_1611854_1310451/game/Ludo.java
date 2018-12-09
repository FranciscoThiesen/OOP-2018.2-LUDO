package com.inf1636_1611854_1310451.game;
import java.awt.Color;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.inf1636_1611854_1310451.util.Savable;
import com.inf1636_1611854_1310451.util.Subject;

public class Ludo implements Savable {

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
		this.initPlayers();

    	this.die.onStateChange.attach((Die die) -> { this.onDieStateChange(die); });
	}
	
	private void initPlayers() {
    	this.players = new Vector<Player>();
		this.players.add(new Player(Color.RED, board.redPiecesTracks, "RED"));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks, "GREEN"));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks, "YELLOW"));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks, "BLUE"));
	}
	
	private void onDieStateChange(Die die) {
		this.onStateChange.notifyAllObservers(this);
	}
	
	// ===========================================
	// SAVE AND LOAD
	// ===========================================
	
	@SuppressWarnings("unchecked")
	public JSONObject saveStateToJSON() {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for(Player player: this.players) {			
			array.add(player.saveStateToJSON());
		}
		obj.put("players", array);
		obj.put("die", this.die.saveStateToJSON());
		obj.put("currentPlayerIndex", this.currentPlayerIndex);
		obj.put("_isEndTurnActionPossible", this._isEndTurnActionPossible);
		obj.put("_isRollActionPossible", this._isRollActionPossible);
		return obj;
	}
	
	public void loadStateFromJSON(JSONObject obj) {
    	JSONArray array = (JSONArray) obj.get("players");
    	this.board = new Board();
    	this.die.loadStateFromJSON((JSONObject) obj.get("die"));
    	this.currentPlayerIndex = ((Long) obj.get("currentPlayerIndex")).intValue();
    	this._isEndTurnActionPossible = (Boolean) obj.get("_isEndTurnActionPossible");
    	this._isRollActionPossible = (Boolean) obj.get("_isRollActionPossible");
		this.setSelectedPiece(null);
    	this.initPlayers();
		for(int i=0; i<array.size(); i++) {			
			this.players.get(i).loadStateFromJSON((JSONObject) array.get(i));
		}
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
	
	public Vector<PieceMovement> getPlacesGivenPieceCanMove(Piece p) {
		Vector<PieceMovement> vec = new Vector<PieceMovement>();
		Vector<PieceMovement> allMoves = this.allMoves(this.getCurrentPlayer());
		for(PieceMovement move: allMoves) {
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
	
	private void executeMove(PieceMovement move) {
		boolean capture = move.isACaptureMovement;
		if( capture ) {
			// There is exactly one piece that will be captured at move.boardSquare
			Piece p = (move.boardSquare.getPieces().firstElement() );
			BoardSquare sanctuary = p.getPieceTrack().get(0);
			PieceMovement backToSanctuary = new PieceMovement( p.getPlayer(), p, sanctuary, false, 0);
			executeMove( backToSanctuary );
		}
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
		
		Vector<PieceMovement> moves = this.getPlacesGivenPieceCanMove(this.getSelectedPiece());
		
		// prioritize movement
		for(PieceMovement move: moves) {
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
			Vector<PieceMovement> allPossibleMoves = this.allMoves(this.getCurrentPlayer());
			if(allPossibleMoves.size() == 0) {
				this.setIsEndTurnActionPossible(true);
			}
		}
		
	}
	
	// ===========================================

	private Vector<PieceMovement> allMoves(Player player) {
		Die die = this.getDie();
		
		if(die.hasBeenUsed()) {
			return new Vector<PieceMovement>();
		}
		
		if(die.getValue() == 5) {
			Vector<Piece> eligeblePieces = player.getPiecesEligebleToTakeOutOfSanctuary();
			if(eligeblePieces.size() > 0) {
				return PieceMovement.generateMovementsFromArray(eligeblePieces, 1, die.getValue());
			}
		}
		if(die.getValue() == 6) {
			Vector<Piece> eligeblePieces = player.getBarrierPiecesThatCanMoveAGivenNumber(6);
			if(eligeblePieces.size() > 0) {
				return PieceMovement.generateMovementsFromArray(eligeblePieces, die.getValue(), die.getValue());
			}
		}
		Vector<Piece> eligeblePieces = player.getPiecesThatCanMoveAGivenNumber(die.getValue());
		return PieceMovement.generateMovementsFromArray(eligeblePieces, die.getValue(), die.getValue());
	}
	
	public void startGame() {
		for(Player player: this.getPlayers()) {
			Vector<Piece> pieces = player.getPieces();
			Piece firstPiece = pieces.get(0);
			firstPiece.moveToBoardSquare(firstPiece.getBoardSquareInNumMoves(1));
		}
		this.setIsRollActionPossible(true);
	}
	
	public void getSaveStateAsString() {
		
	}

}
