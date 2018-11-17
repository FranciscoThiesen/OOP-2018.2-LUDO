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

	public int getPathIndex()
	{
		return this.pathIndex;
	}

	public Vector<Integer> getPieceTrack()
	{
		return this.pieceTrack;
	}

	// This function needs to handle some other game specifications, but now its a simple version
	public boolean canMove(int numMoves)
	{
		int desiredPosition = pathIndex + numMoves;
		int maxPosition = pieceTrack.size() - 1;
		return desiredPosition <= maxPosition;
	}

}
