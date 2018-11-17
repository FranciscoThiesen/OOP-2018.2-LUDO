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

	public Vector<Integer> availableMove( Piece p, int diceValue )
	{
		Vector<Integer> moves = new Vector<Integer>() ;
		boolean ok = p.canMove(diceValue);
		if(ok)
		{
			Vector<Integer> path = p.getPieceTrack();
			int index = p.getPathIndex() + diceValue;
			int boardSquareIndex = path.get(index);
			moves.add(boardSquareIndex);
		}
		return moves;
	}

}
