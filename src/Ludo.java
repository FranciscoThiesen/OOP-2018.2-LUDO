import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Optional;

public class Ludo {

	private Board board;
	private Die die;
	private Vector<Player> players;
	private int currentPlayerIndex;
	private Piece selectedPiece;

	public Subject<Player> onPlayerChange = new Subject<Player>();
	public Subject<Die> onDieInfoChange = new Subject<Die>();
	public SubjectVoid onTurnComplete = new SubjectVoid();
	public Subject<Vector<PiecePositioningInfo>> onPiecesPositionChange = new Subject<Vector<PiecePositioningInfo>>();
	public Subject<Piece> onPieceSelect = new Subject<Piece>();
	public Subject<Piece> onPieceUnselect = new Subject<Piece>();

	private static Ludo instance = null;

	public static Ludo getInstance() {
		if(Ludo.instance == null) {
			Ludo.instance = new Ludo();
		}
		return Ludo.instance;
	}

	private Ludo() {
		this.board = new Board();
		this.die = new Die();
		this.players = new Vector<Player>();
		this.players.add(new Player(Color.RED, board.redPiecesTracks, "RED"));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks, "GREEN"));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks, "BLUE"));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks, "YELLOW"));
		this.currentPlayerIndex = 0;

    	this.die.onStateChange.attach((Die die) -> { this.onDieStateChange(die); });
	}
	
	public void onDieStateChange(Die die) {
		this.onDieInfoChange.notifyAllObservers(die);
	}
	
	public boolean hasPieceSelected() {
		return this.selectedPiece != null;
	}
	
	public Piece getPieceSelected() {
		return this.selectedPiece;
	}

	public boolean isPieceSelected(Piece piece) {
		return this.selectedPiece == piece;
	}

	public void selectPiece(Piece piece) {
		this.selectedPiece = piece;
		this.onPieceSelect.notifyAllObservers(this.selectedPiece);
	}

	public void unselectPiece() {
		Piece piece = this.selectedPiece;
		this.selectedPiece = null;
		this.onPieceUnselect.notifyAllObservers(piece);
	}

	public void clickOnPiece(Piece piece) {
		if(piece.getPlayer() == this.players.get(this.currentPlayerIndex)) {
			this.selectPiece(piece);
		}
	}

	public Vector<Piece> getPiecesArray() {
		Vector<Piece> vec = new Vector<Piece>();
		for(Player player: this.players) {
			for(Piece piece: player.getPieces()) {
				vec.add(piece);
			}
		}
		return vec;
	}

	public void onClickBoardSquare(BoardSquare b) {
		for(Piece piece: this.getPiecesArray()) {
			if(this.board.squares.get(piece.getBoardTrueIndex()) == b) {
				this.clickOnPiece(piece);
			}
		}
		if(	this.hasPieceSelected() &&
			this.getPieceSelected().getPlayer() == this.getCurrentPlayer()) {
			Vector<PossiblePieceMovement> moves = this.getPlacesGivenPieceCanMove(this.getPieceSelected());
			for(PossiblePieceMovement move: moves) {
				if(move.boardSquare == b && this.isPieceSelected(move.piece)) {
					this.movePieceToBoardSquare(move.piece, move.boardSquare);
					this.die.use();
					this.onPiecesPositionChange.notifyAllObservers(this.getPiecesInformation());
					this.unselectPiece();
				}
			}
		}
	}
	
	public void movePieceToBoardSquare(Piece p, BoardSquare b) {
		Vector<Integer> pieceTrack = p.getPieceTrack();
		for(int i=0; i<pieceTrack.size(); i++) {
			if(this.board.squares.get(pieceTrack.get(i)) == b) {
				p.setPathIndex(i);
			}
		}
	}

	public Vector<PiecePositioningInfo> getPiecesInformation() {
		Vector<PiecePositioningInfo> vec = new Vector<PiecePositioningInfo>();
		for(Piece piece: this.getPiecesArray()) {
			PiecePositioningInfo info = new PiecePositioningInfo(piece,
					this.board.squares.get(piece.getBoardTrueIndex()),
					piece.getPlayer());
			vec.add(info);
		}
		return vec;
	}

	public Vector<BoardSquare> getBoardSquareArray() {
		return this.board.squares;
	}

	public void nextPlayer() {
		this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 4;
		this.onPlayerChange.notifyAllObservers(this.players.get(this.currentPlayerIndex));
	}

	public String getCurrentPlayerName() {
		return this.players.get(this.currentPlayerIndex).getName();
	}

	public void rollDice() {
		this.die.roll();
		Integer notification = new Integer(this.die.getValue());
		this.onTurnComplete.notifyAllObservers();
	}
	
	public Die getDie() {
		return this.die;
	}

	public void run() {

	}

	public ArrayList<Piece> getAllPiecesOnASquare( int targetSquareIndex )
	{
		ArrayList<Piece> piecesOnTarget = new ArrayList<Piece>();
		for( Player x : players)
		{
			Vector<Piece> pieceVector = x.getPieces();
			for( Piece p : pieceVector)
			{
				Vector<Integer> track = p.getPieceTrack();
				int trackIndex = p.getPathIndex();
				Integer b = track.get(trackIndex);
				piecesOnTarget.add( p );
			}
		}
		return piecesOnTarget;
	}

	// Essa funcao visa informar se uma movimentacao de uma determinada peca eh valida. Atentendo as restricoes de barreira descritas nas regras
	public boolean isValidMove( Piece playerPiece, int totalMoves) {
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
	public boolean playerHasBarrier(Player playerNumber) {
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
	public boolean makesCapture(Piece movingPiece, int targetBoardSquare) {
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

	// Essa funcao assume que uma peca vai ser capturada! eh necessario saber que makesCapture( movingPiece, targetBoardSquare) == true
	// Usei optional so pra ele nao reclamar que o retorno pode ser possivelmente vazio, mesmo eu sabendo que sempre vai retornar.
	// Criar um retorno vazio em cima, implicaria ter que criar um construtor default de peca, que nao faz muito sentido para nossa aplicacao
	public Optional<Piece> capturedPiece(Piece movingPiece, int targetBoardSquare) {
		// assert(makesCapture(movingPiece, targetBoardSquare) == true
		Player pieceOwner = movingPiece.getPlayer();
		Optional<Piece> ret = Optional.empty();
		for(Player enemy : players) {
			if(enemy == pieceOwner) continue;
			for(Piece p : enemy.getPieces() ) {
				int piecePathIndex = p.getPathIndex();
				int pieceBoardSquareIndex = p.getPieceTrack().get( piecePathIndex );
				if(pieceBoardSquareIndex == targetBoardSquare) {
					ret = Optional.of(p);
					break;
				}
			}
		}
		return ret;
	}

	public Player getCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}

	public Vector<PossiblePieceMovement> getPlacesGivenPieceCanMove(Piece p) {
		Vector<PossiblePieceMovement> vec = new Vector<PossiblePieceMovement>();
		// Deixei dessa forma por motivos de como foi implementado anteriormente com um dado extra, podemos mudar essa logica futuramente
		for(Integer diceRoll : this.die.availableDieValue()) {
			Vector<Pair<Piece, Pair<Integer, Boolean>>> allMoves = this.allMoves(this.getCurrentPlayer(), diceRoll);
			for(Pair<Piece, Pair<Integer, Boolean>> res: allMoves) {
				if(res.first == p) {
					PossiblePieceMovement move = new PossiblePieceMovement();
					move.player = p.getPlayer();
					move.piece = p;
					move.boardSquare = this.board.squares.get(p.getPieceTrack().get(res.second.first));
					move.isACaptureMovement = res.second.second;
					move.diceRoll = diceRoll;
					vec.add(move);
				}
			}
		}
		return vec;
	}

	/* Essa funcao vai retornar todas as jogadas possiveis por parte de um jogador
		vet[i] -> (Peca, (indice do path dela para qual ela pode se mover, flag que diz se ocorre captura ou nao ) )
	 */
	public Vector< Pair< Piece, Pair<Integer, Boolean> > > allMoves(Player playerNum, int diceValue) {
		// Casos especiais-> Dice Value = 5 -> Tem que tirar um peao do santuario se for possivel, senao joga qlqr outra peca normalmente
		Vector< Pair< Piece, Pair<Integer, Boolean> > > allPossibleMoves = new Vector<>(); // Sintaxe linda, ele infere o tipo (:
		Vector<Piece> pieces = playerNum.getPieces();
		if(diceValue == 5) {
			boolean canMovePieceFromSanctuary = false;
			for(Piece p : pieces ) {
				if(p.getPathIndex() == 0) {
					if(isValidMove(p, 1) ) {
						canMovePieceFromSanctuary = true;
						Pair< Piece, Pair<Integer, Boolean > > move = new Pair< Piece, Pair<Integer, Boolean > >();
						move.second = new Pair<Integer, Boolean>();
						move.first = p;
						move.second.first = 1;
						move.second.second = makesCapture(p, p.getPieceTrack().get(1) );
						allPossibleMoves.add(move);
					}
				}
			}
			if(canMovePieceFromSanctuary) return allPossibleMoves;
		}
		if(diceValue == 6) {
			boolean hasBarrier = playerHasBarrier(playerNum);
			boolean canMoveBarrierPiece = false;
			if(hasBarrier) {
				for(Piece p1 : pieces) {
					for(Piece p2 : pieces) {
						if(p1 != p2 && p1.getPathIndex() == p2.getPathIndex() && p1.getPathIndex() > 0 && p1.getPathIndex() < board.trackLength - 1) {
							if(isValidMove(p1, diceValue) ) {
								canMoveBarrierPiece = true;
								Pair< Piece, Pair<Integer, Boolean > > move = new Pair< Piece, Pair<Integer, Boolean > >();
								move.second = new Pair<Integer, Boolean>();
								move.first = p1;
								move.second.first = p1.getPathIndex() + diceValue;
								move.second.second = makesCapture(p1, p1.getPieceTrack().get( move.second.first) );
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
				Pair< Piece, Pair<Integer, Boolean > > move = new Pair< Piece, Pair<Integer, Boolean > >();
				move.second = new Pair<Integer, Boolean>();
				move.first = p;
				move.second.first = p.getPathIndex() + 1;
				move.second.second = makesCapture(p, p.getPieceTrack().get( move.second.first) );
				allPossibleMoves.add(move);
			}
			else if(p.getPathIndex() > 0 && isValidMove(p, diceValue) ) {
				Pair< Piece, Pair<Integer, Boolean > > move = new Pair< Piece, Pair<Integer, Boolean > >();
				move.second = new Pair<Integer, Boolean>();
				move.first = p;
				move.second.first = p.getPathIndex() + diceValue;
				move.second.second = makesCapture(p, p.getPieceTrack().get( move.second.first) );
				allPossibleMoves.add(move);
			}
		}
		return allPossibleMoves;
	}

}
