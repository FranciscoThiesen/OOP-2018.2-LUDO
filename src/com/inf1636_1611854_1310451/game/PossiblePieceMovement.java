package com.inf1636_1611854_1310451.game;

public class PossiblePieceMovement {
	public Player player;
	public Piece piece;
	public BoardSquare boardSquare;
	public boolean isACaptureMovement;
	public Integer diceRoll;
	
	public PossiblePieceMovement(	Player player,
									Piece piece,
									BoardSquare boardSquare,
									boolean isACaptureMovement,
									Integer diceRoll) {
		this.player = player;
		this.piece = piece;
		this.boardSquare = boardSquare;
		this.isACaptureMovement = isACaptureMovement;
		this.diceRoll = diceRoll;
	}
}
