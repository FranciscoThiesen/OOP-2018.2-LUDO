package com.inf1636_1611854_1310451.game;
import java.awt.*;
//import javax.swing.*;
import java.util.*;

public class Player {
	private Color color;
	private Vector<Piece> pieces;
	private String name;

	public Player(Color c, Vector<Vector<Integer> > tracks, String name) {
		this.color = c;
		this.pieces = new Vector<Piece>();
		this.pieces.add(new Piece(this, tracks.get(0) ) );
		this.pieces.add(new Piece(this, tracks.get(1) ) );
		this.pieces.add(new Piece(this, tracks.get(2) ) );
		this.pieces.add(new Piece(this, tracks.get(3) ) );
		this.name = name;
	}

	public Vector<Piece> getPieces()
	{
		return this.pieces;
	}
	
	public String getName() {
		return this.name;
	}

	public Color getColor() { return this.color; }
}
