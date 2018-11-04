import java.awt.*;
//import javax.swing.*;
import java.util.*;

public class Player {
	private Color color;
	private Vector<Piece> pieces;
	private Vector<BoardSquare> piecePath;
	
	public Player(Color c) {
		this.color = c;
		this.pieces = new Vector<Piece>();
		this.piecePath = new Vector<BoardSquare>();
	}
}
