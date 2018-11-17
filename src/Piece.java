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

	public int getPathIndex() { return this.pathIndex; }

	public Vector<Integer> getPieceTrack() { return this.pieceTrack; }

	public Player getPlayer() { return this.player; }
}
