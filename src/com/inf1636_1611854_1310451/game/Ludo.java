package com.inf1636_1611854_1310451.game;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Vector;

import com.inf1636_1611854_1310451.util.Pair;
import com.inf1636_1611854_1310451.util.Subject;
import com.inf1636_1611854_1310451.util.SubjectVoid;

import java.util.ArrayList;
import java.util.Optional;

public class Ludo {

	private Board board = new Board();
	private Die die = new Die();
	private Vector<Player> players = new Vector<Player>();
	private int currentPlayerIndex = 0;
	private Piece selectedPiece;
	
	public Subject<Ludo> onStateChange = new Subject<Ludo>();

	// ===========================================
	// SINGLETON DESIGN PATTERN
	// ===========================================
	
	private static Ludo instance = null;

	public static Ludo getInstance() {
		if(Ludo.instance == null) {
			Ludo.instance = new Ludo();
		}
		return Ludo.instance;
	}
	
	// ===========================================
	// CONSTRUCTOR AND STATE PROPAGATORS
	// ===========================================

	private Ludo() {
		this.players.add(new Player(Color.RED, board.redPiecesTracks, "RED"));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks, "GREEN"));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks, "BLUE"));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks, "YELLOW"));

    	this.die.onStateChange.attach((Die die) -> { this.onDieStateChange(die); });
	}
	
	private void onDieStateChange(Die die) {
		this.onStateChange.notifyAllObservers(this);
	}
	
	// ===========================================
	// GETTERS AND SETTERS
	// ===========================================
	
	private void setSelectedPiece(Piece piece) {
		this.selectedPiece = piece;
		this.onStateChange.notifyAllObservers(this);
	}
	
	public Piece getSelectedPiece() {
		return this.selectedPiece;
	}
	
	private void setCurrentPlayerIndex(int index) {
		this.currentPlayerIndex = index;
		this.onStateChange.notifyAllObservers(this);
	}
	
	private int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	// ===========================================
	// ACCESS METHODS
	// ===========================================
	
	// -------------- private ------------
	private Vector<Piece> getPiecesArray() {
		Vector<Piece> piecesArray = new Vector<Piece>();
		for(Player player: this.players) {
			for(Piece piece: player.getPieces()) {
				piecesArray.add(piece);
			}
		}
		return piecesArray;
	}
	
	//--------------- public -------------
	
	public Vector<BoardSquare> getBoardSquareArray() {
		return this.board.squares;
	}
	
	public Player getCurrentPlayer() {
		return this.players.get(this.getCurrentPlayerIndex());
	}
	
	public boolean hasPieceSelected() {
		return this.selectedPiece != null;
	}

	public boolean isPieceSelected(Piece piece) {
		return this.selectedPiece == piece;
	}
	
	public Die getDie() {
		return this.die;
	}
	
	public Vector<PiecePositioningInfo> getPiecesInformation() {
		Vector<PiecePositioningInfo> piecesInformation = new Vector<PiecePositioningInfo>();
		for(Piece piece: this.getPiecesArray()) {
			PiecePositioningInfo info = new PiecePositioningInfo(piece,
					this.board.squares.get(piece.getBoardTrueIndex()),
					piece.getPlayer());
			piecesInformation.add(info);
		}
		return piecesInformation;
	}
	
	public Vector<PossiblePieceMovement> getPlacesGivenPieceCanMove(Piece p) {
		Vector<PossiblePieceMovement> vec = new Vector<PossiblePieceMovement>();
		Vector<PossiblePieceMovement> allMoves = this.allMoves(this.getCurrentPlayer());
		for(PossiblePieceMovement move: allMoves) {
			if(move.piece == p) {
				vec.add(move);
			}
		}
		return vec;
	}
	
	// ===========================================
	// STATE CHANGERS
	// ===========================================

	public void unselectPiece() {
		this.setSelectedPiece(null);
	}
	
	public void clickOnPiece(Piece piece) {
		if(piece.getPlayer() == this.players.get(this.getCurrentPlayerIndex())) {
			this.setSelectedPiece(piece);
		}
	}
	
	public void clickBoardSquare(BoardSquare b) {
		for(Piece piece: this.getPiecesArray()) {
			if(this.board.squares.get(piece.getBoardTrueIndex()) == b) {
				this.clickOnPiece(piece);
			}
		}
		if(	this.hasPieceSelected() &&
			this.getSelectedPiece().getPlayer() == this.getCurrentPlayer()) {
			Vector<PossiblePieceMovement> moves = this.getPlacesGivenPieceCanMove(this.getSelectedPiece());
			for(PossiblePieceMovement move: moves) {
				if(move.boardSquare == b && this.isPieceSelected(move.piece)) {
					move.piece.moveToBoardSquare(this.board, move.boardSquare);
					this.die.use();
					this.onStateChange.notifyAllObservers(this);
					this.unselectPiece();
				}
			}
		}
	}
	
	public void endTurn() {
		this.setCurrentPlayerIndex((this.getCurrentPlayerIndex() + 1) % 4); 
	}
	
	public void rollDie() {
		this.die.roll();
	}
	
	// ===========================================

	// Essa funcao visa informar se uma movimentacao de uma determinada peca eh valida.
	// Atentendo as restricoes de barreira descritas nas regras
	private boolean isValidMove( Piece playerPiece, int totalMoves) {
		int startPathIndex = playerPiece.getPathIndex();
		if( startPathIndex + totalMoves >= board.trackLength) return false; // Iria para fora do tabuleiro
		Player player = playerPiece.getPlayer();
		// Chegando por uma barreira do inimigo no meio do trajeto
		for(int inc = 1; inc < totalMoves; ++inc ) {
			int pathBoardSquareIdx = playerPiece.getPieceTrack().get( startPathIndex + inc);
			for( Player enemy : players) {
				if(enemy.getColor() == player.getColor() ) continue;
				int count = 0;
				for(Piece p : enemy.getPieces() ) {
					int pBoardSquareIdx = p.getPieceTrack().get( p.getPathIndex() );
					if(pBoardSquareIdx == pathBoardSquareIdx) ++count;
				}
				if(count >= 2) return false;
			}
		}
		// Checagem se a posicao final do trajeto comporta mais um peao
		if(startPathIndex + totalMoves == board.trackLength - 1) return true;
		int finalPathIdx = playerPiece.getPieceTrack().get( startPathIndex + totalMoves);
		int generalCount = 0;
		for( Player play : players) {
			for(Piece p : play.getPieces() ) {
				int pBoardSquareIdx = p.getPieceTrack().get(p.getPathIndex());
				if(pBoardSquareIdx == finalPathIdx) generalCount++;
			}
		}
		if(generalCount >= 2) return false; // Isso indica que a casa esta cheia, seja uma barreira propria ou do inimigo ou uma casa de abrigo ou saida que ja possui 2 peoes

		// Checando se estamos tentando ir para a casa de saida do player e se ele ja tem uma peca dessa cor la
		if(startPathIndex + totalMoves == 1) {
			for(Piece p : player.getPieces() ) {
				if(p != playerPiece && p.getPathIndex() == 1) return false;
			}
		}
		return true;
	}

	// Lembrar que na ultima casa nao existe barreira. Podemos ter ate mesmo todos os peoes de uma cor na ultima casa da cor. Peoes com pathIndex = 0 estao no santuario, tambem nao formam barreira
	private boolean playerHasBarrier(Player playerNumber) {
		Vector<Piece> pieces = playerNumber.getPieces();
		for(Piece p1 : pieces) {
			for(Piece p2 : pieces) {
				if(p1 == p2) continue;
				if(p1.getPathIndex() == p2.getPathIndex() && p1.getPathIndex() < board.trackLength - 1 && p1.getPathIndex() > 0) return true;
			}
		}
		return false;
	}

	// If a move the piece to the target position, will it make a capture?
	private boolean makesCapture(Piece movingPiece, int targetBoardSquare) {
		int shelterSquares[] = { 25, 38, 51, 64 };
		int exitSquares[] = { 16, 29, 42, 55 };
		for(int x : shelterSquares) if(targetBoardSquare == x) return false; // Nao pode capturar em casa abrigo
		boolean isExitSquare = false;
		for(int x : exitSquares) if(x == targetBoardSquare) isExitSquare = true;
		Player pieceOwner = movingPiece.getPlayer();
		for(Player enemy : players) {
			if(enemy != pieceOwner) {
				Color enemyColor = enemy.getColor();
				for(Piece p : enemy.getPieces() ) {
					int piecePathIndex = p.getPathIndex();
					int pieceBoardSquareIndex = p.getPieceTrack().get( p.getPathIndex() );
					Color pieceColor = enemy.getColor();
					if(pieceBoardSquareIndex == targetBoardSquare) {
						// Pode acontecer a captura, se nao for casa de saida
						// Ou se for uma casa de saida, mas o peao que esta la nao eh da cor correspondente
						if(!isExitSquare) return true;
						if(targetBoardSquare == 16 && pieceColor == Color.RED) return false;
						else if(targetBoardSquare == 29 && pieceColor == Color.GREEN) return false;
						else if(targetBoardSquare == 42 && pieceColor == Color.YELLOW) return false;
						else if(targetBoardSquare == 55 && pieceColor == Color.BLUE) return false;
						else return true;

					}
				}
			}
		}
		return false; // Se chegou aqui eh porque nao existe peca pra ser capturada
	}

	// Essa funcao vai retornar todas as jogadas possiveis por parte de um jogador
	// vet[i] -> (Peca, (indice do path dela para qual ela pode se mover, flag que diz se ocorre captura ou nao ) )
	//
	private Vector<PossiblePieceMovement> allMoves(Player player) {
		// Casos especiais:
		// Dice Value = 5 -> Tem que tirar um peao do santuario se for possivel, senao joga qlqr outra peca normalmente
		Vector<PossiblePieceMovement> allPossibleMoves = new Vector<PossiblePieceMovement>();
		Vector<Piece> pieces = player.getPieces();
		Die die = this.getDie();
		if(die.hasBeenUsed()) {
			return allPossibleMoves;
		}
		if(die.getValue() == 5) {
			boolean canMovePieceFromSanctuary = false;
			for(Piece p : pieces) {
				if(p.getPathIndex() == 0) {
					if(isValidMove(p, 1) ) {
						canMovePieceFromSanctuary = true;
						PossiblePieceMovement move = new PossiblePieceMovement(	player,
																				p,
																				p.pathIndexToBoardSquare(this.board, 1),
																				this.makesCapture(p, p.getPieceTrack().get(1)),
																				die.getValue());
						allPossibleMoves.add(move);
					}
				}
			}
			if(canMovePieceFromSanctuary) return allPossibleMoves;
		}
		if(die.getValue() == 6) {
			boolean hasBarrier = playerHasBarrier(player);
			boolean canMoveBarrierPiece = false;
			if(hasBarrier) {
				for(Piece p1 : pieces) {
					for(Piece p2 : pieces) {
						if(p1 != p2 && p1.getPathIndex() == p2.getPathIndex() && p1.getPathIndex() > 0 && p1.getPathIndex() < board.trackLength - 1) {
							if(isValidMove(p1, die.getValue()) ) {
								canMoveBarrierPiece = true;
								PossiblePieceMovement move = new PossiblePieceMovement(	player,
																p1,
																p1.pathIndexToBoardSquare(this.board, p1.getPathIndex() + die.getValue()),
																this.makesCapture(p1, p1.getPieceTrack().get(p1.getPathIndex() + die.getValue()) ),
																die.getValue());
								allPossibleMoves.add(move);
							}
						}
					}
				}
			}
			if(canMoveBarrierPiece) return allPossibleMoves;
		}
		// Se cheguei aqui, eh porque nao cai em nenhuma das regras especiais
		for(Piece p : pieces) {
			if(p.getPathIndex() == 0 && isValidMove(p, 1) ) {
				PossiblePieceMovement move = new PossiblePieceMovement(	player,
																			p,
																			p.pathIndexToBoardSquare(this.board, p.getPathIndex() + 1),
																			this.makesCapture(p, p.getPieceTrack().get(p.getPathIndex() + 1)),
																			die.getValue());
				allPossibleMoves.add(move);
			}
			else if(p.getPathIndex() > 0 && isValidMove(p,  die.getValue()) ) {
				PossiblePieceMovement move = new PossiblePieceMovement(	player,
																			p,
																			p.pathIndexToBoardSquare(this.board, p.getPathIndex() + die.getValue()),
																			this.makesCapture(p, p.getPieceTrack().get(p.getPathIndex() +  die.getValue())),
																			die.getValue());
				allPossibleMoves.add(move);
			}
		}
		return allPossibleMoves;
	}

}
