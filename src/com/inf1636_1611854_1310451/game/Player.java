package com.inf1636_1611854_1310451.game;
import java.awt.*;
//import javax.swing.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.inf1636_1611854_1310451.util.Savable;

public class Player implements Savable {
	private Color color;
	private Vector<Piece> pieces;
	private String name;

	public Player(Color c, Vector<Vector<BoardSquare> > tracks, String name) {
		this.color = c;
		this.pieces = new Vector<Piece>();
		this.pieces.add(new Piece(this, tracks.get(0) ) );
		this.pieces.add(new Piece(this, tracks.get(1) ) );
		this.pieces.add(new Piece(this, tracks.get(2) ) );
		this.pieces.add(new Piece(this, tracks.get(3) ) );
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject saveStateToJSON() {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for(Piece piece: this.pieces) {			
			array.add(piece.saveStateToJSON());
		}
		obj.put("name", this.name);
		obj.put("pieces", array);
		return obj;
	}
	
	public void loadStateFromJSON(JSONObject obj) {
		JSONArray array = (JSONArray) obj.get("pieces");
		for(int i=0; i<array.size(); i++) {			
			this.pieces.get(i).loadStateFromJSON((JSONObject) array.get(i));
		}
		this.name = (String) obj.get("name");
	}

	public Vector<Piece> getPieces()
	{
		return this.pieces;
	}
	
	public String getName() {
		return this.name;
	}

	public Color getColor() { return this.color; }

	public Vector<Piece> getPiecesEligebleToTakeOutOfSanctuary() {
		Vector<Piece> eligleblePieces = new Vector<Piece>();
		for(Piece piece: this.getPieces()) {
			if(piece.isEligebleToExitSanctuary()) {
				eligleblePieces.add(piece);
			}
		}
		return eligleblePieces;
	}

	public Vector<Piece> getPiecesThatCanMoveAGivenNumber(int numMovements) {
		Vector<Piece> eligleblePieces = new Vector<Piece>();
		for(Piece piece: this.getPieces()) {
			if(piece.isValidMove(numMovements) && !piece.isInSanctuary()) {
				eligleblePieces.add(piece);
			}
		}
		return eligleblePieces;
	}

	public Vector<Piece> getBarrierPiecesThatCanMoveAGivenNumber(int numMovements) {
		Vector<Piece> eligleblePieces = new Vector<Piece>();
		for(Piece piece: this.getPieces()) {
			if(piece.isPartOfABarrier() && piece.isValidMove(numMovements)) {
				eligleblePieces.add(piece);
			}
		}
		return eligleblePieces;
	}
}
