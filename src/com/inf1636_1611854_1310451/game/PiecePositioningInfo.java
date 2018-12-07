package com.inf1636_1611854_1310451.game;
import java.awt.*;

public class PiecePositioningInfo {
	public Piece piece;
	public BoardSquare position;
	public Player player;
	
	public PiecePositioningInfo(Piece piece, BoardSquare position, Player player) {
		this.piece = piece;
		this.position = position;
		this.player = player;
	}
}
