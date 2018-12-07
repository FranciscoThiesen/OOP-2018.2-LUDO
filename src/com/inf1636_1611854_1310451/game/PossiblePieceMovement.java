package com.inf1636_1611854_1310451.game;

import java.util.*;

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
	
	public static Vector<PossiblePieceMovement> generateMovementsFromArray(Vector<Piece> pieces,
																			int numMovements,
																			int diceRoll) {
		Vector<PossiblePieceMovement> generatedArray = new Vector<PossiblePieceMovement>();
		for(Piece piece: pieces) {
			BoardSquare targetBoardSquare = piece.getBoardSquareInNumMoves(numMovements);
			generatedArray.add(new PossiblePieceMovement(piece.getPlayer(),
														 piece,
														 targetBoardSquare,
														 piece.makesCapture(targetBoardSquare),
														 diceRoll));
		}
		return generatedArray;
	}
}
