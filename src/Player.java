import java.awt.*;
//import javax.swing.*;
import java.util.*;

public class Player {
	private Color color;
	private Vector<Piece> pieces;

	public Player(Color c, Vector<Vector<Integer> > tracks) {
		this.color = c;
		this.pieces = new Vector<Piece>();
		this.pieces.add(new Piece(this, tracks.get(0) ) );
		this.pieces.add(new Piece(this, tracks.get(1) ) );
		this.pieces.add(new Piece(this, tracks.get(2) ) );
		this.pieces.add(new Piece(this, tracks.get(3) ) );
	}

	public Vector<Piece> getPieces()
	{
		return this.pieces;
	}

}
