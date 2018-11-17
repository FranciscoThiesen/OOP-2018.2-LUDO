import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.ArrayList;

public class Ludo {
	
	private Board board;
	private Dice dice;
	private Vector<Player> players;
	private int currentPlayerIndex;
	
	public Ludo() {
		this.board = new Board();
		this.dice = new Dice();
		this.players = new Vector<Player>();
		this.players.add(new Player(Color.RED, board.redPiecesTracks));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks));
		this.currentPlayerIndex = 0;
	}
	
	public Vector<BoardSquare> getBoardSquareArray() {
		return this.board.squares;
	}
	
    public void run() {        
        System.out.print("Rolando os dados...\n");
        this.dice.roll();
        System.out.printf("Dado 1 = %d\n", this.dice.getFirst());
        System.out.printf("Dado 2 = %d\n", this.dice.getSecond());
        
        System.out.print("Rolando os dados...\n");
        this.dice.roll();
        System.out.printf("Dado 1 = %d\n", this.dice.getFirst());
        System.out.printf("Dado 2 = %d\n", this.dice.getSecond());
        
        System.out.print("Rolando os dados...\n");
        this.dice.roll();
        System.out.printf("Dado 1 = %d\n", this.dice.getFirst());
        System.out.printf("Dado 2 = %d\n", this.dice.getSecond());
        
        System.out.print("Rolando os dados...\n");
        this.dice.roll();
        System.out.printf("Dado 1 = %d\n", this.dice.getFirst());
        System.out.printf("Dado 2 = %d\n", this.dice.getSecond());
        
        System.out.print("Rolando os dados...\n");
        this.dice.roll();
        System.out.printf("Dado 1 = %d\n", this.dice.getFirst());
        System.out.printf("Dado 2 = %d\n", this.dice.getSecond());

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

	/* Essa funcao retorna a peca, e pra qual indice do vetor dela de movimentos ela pode ir... Isso para cada peca que for possivel mover.
	* Eh melhor implementar dessa forma, do que implementar a posicao absoluta, pois temos que "andar" posicoes no vetor tracks de cada peca
	* se formos seguir a logica de implementacao estipulada ate aqui*/
	public ArrayList< Pair<Piece, Integer> > getAllPossibleMoviments(Player playerNumber, int diceValue) {


		ArrayList<Pair<Piece, Integer>> possibleMoves = new ArrayList<Pair<Piece, Integer>>();
		// Se um jogador tirar 5, ele deve andar com um peao que esta no santuario ( se for possivel )

		// Posicoes de abrigo
		int shelterPositions[] = {25, 38, 51, 64};
		int exitPositions[] = {16, 29, 42, 55};

		if (diceValue == 5) {
			boolean hasPieceAtSanctuary = false;
			boolean hasPieceOnStartPos = false;
			Vector<Piece> pieces = playerNumber.getPieces();
			for (Piece p : pieces) {
				int idx = p.getPathIndex();
				if (idx == 0) hasPieceAtSanctuary = true;
				if (idx == 1) hasPieceOnStartPos = true;
			}

			if (hasPieceAtSanctuary == true && hasPieceOnStartPos == false) {
				for (Piece p : pieces) {
					if (p.getPathIndex() == 0) {
						Pair move = new Pair<Piece, Integer>();
						move.first = p;
						move.second = 1; // Andar para a posicao de saida do santuario
						possibleMoves.add(move);
					}
				}
				return possibleMoves;
			}
		}

		/* Esse caso precisa ser tratado especialmente. Pois se o jogaodor tiver uma barreira, ele é obrigado a desfazê-la se for possível.
		   Para fins de definição, uma barreira é quando um jogador possui duas de suas peças em uma única casa.
		 */

		if (diceValue == 6) {
			/* Vou fazer O(n^2), pois n = 4 e não vai mudar nada */
			int firstBarrier = -1;
			int secondBarrier = -1; // É quase um milagre, mas pode acontecer..

			Vector<Piece> pieces = playerNumber.getPieces();
			// Lembrar que pecas na ultima casa nao formam barreira
			for (int i = 0; i < 4; ++i) {
				Vector<Integer> firstPieceTrack = pieces.get(i).getPieceTrack();
				int firstPieceIndex = pieces.get(i).getPathIndex();
				int firstPiecePosition = firstPieceTrack.get(firstPieceIndex);
				if (firstPieceIndex == board.trackLength - 1 || firstPieceIndex == 0) continue;

				for (int j = i + 1; j < 4; ++j) {
					Vector<Integer> secondPieceTrack = pieces.get(j).getPieceTrack();
					int secondPieceIndex = pieces.get(j).getPathIndex();
					int secondPiecePosition = secondPieceTrack.get(secondPieceIndex);
					if (secondPieceIndex == board.trackLength - 1 || secondPieceIndex == 0) continue;
					if (firstPiecePosition == secondPiecePosition) {
						if (firstBarrier == -1) {
							firstBarrier = firstPieceIndex;
						} else {
							secondBarrier = firstPieceIndex; // acho que eh muito raro isso acontecer
						}
					}
				}
			}

			if (firstBarrier != -1) {
				int nextPositionPathIndex = firstBarrier + 6;
				if (nextPositionPathIndex < board.trackLength) {
					// Agora tenho que fazer 2-checks. 1- Pra ver se ja nao tem 2 peoes na casa destino, o outro pra ver se nao tem nenhuma barreira de outro player no meio do caminho
					// Vou checar pra ver se nao existe barreira de nenhum ini
					Piece anyPiece = pieces.get(0);
					int beginningIndex = firstBarrier;
					Vector<Integer> piecePath = anyPiece.getPieceTrack();
					boolean obstruction = false;
					for (int inc = 1; inc <= 6; ++inc) {
						int boardSquareNumber = piecePath.get(beginningIndex + inc);
						int lastPositionCrowd = 0;
						for (Player enemy : players) {
							int count = 0;
							Vector<Piece> enemyPieces = enemy.getPieces();
							for (Piece x : enemyPieces) {
								int enemyIndex = x.getPathIndex();
								Vector<Integer> enemyPath = x.getPieceTrack();
								if (enemyPath.get(enemyIndex) == boardSquareNumber) {
									++count;
									if (inc == 6) {
										lastPositionCrowd++;
									}
								}
								if (count >= 2 && enemy != playerNumber) {
									obstruction = true;
									break;
								}
							}
						}
						if (inc == 6 && lastPositionCrowd >= 2) {
							obstruction = true;
							break;
						}
					}
					if (obstruction == false) {
						Pair<Piece, Integer> move = new Pair<>();
						for (Piece p : pieces) {
							if (p.getPathIndex() == firstBarrier) {
								move.first = p;
								move.second = p.getPathIndex() + 6;
								possibleMoves.add(move);
							}
						}
					}
				}
			}
			if (secondBarrier != -1) {
				int nextPositionPathIndex = secondBarrier + 6;
				if (nextPositionPathIndex < board.trackLength) {
					Piece anyPiece = pieces.get(0);
					int beginningIndex = firstBarrier;
					Vector<Integer> piecePath = anyPiece.getPieceTrack();
					boolean obstruction = false;
					for (int inc = 1; inc <= 6; ++inc) {
						int boardSquareNumber = piecePath.get(beginningIndex + inc);
						int lastPositionCrowd = 0;
						for (Player enemy : players) {
							int count = 0;
							Vector<Piece> enemyPieces = enemy.getPieces();
							for (Piece x : enemyPieces) {
								int enemyIndex = x.getPathIndex();
								Vector<Integer> enemyPath = x.getPieceTrack();
								if (enemyPath.get(enemyIndex) == boardSquareNumber) {
									++count;
									if (inc == 6) {
										lastPositionCrowd++;
									}
								}
								if (count >= 2 && enemy != playerNumber) {
									obstruction = true;
									break;
								}
							}
						}
						if (inc == 6 && lastPositionCrowd >= 2) {
							obstruction = true;
							break;
						}
					}
					if (obstruction == false) {
						Pair<Piece, Integer> move = new Pair<>();
						for (Piece p : pieces) {
							if (p.getPathIndex() == firstBarrier) {
								move.first = p;
								move.second = p.getPathIndex() + 6;
								possibleMoves.add(move);
							}
						}
					}
				}
			}
			if (!possibleMoves.isEmpty()) return possibleMoves; // Caso N = 6
		}

		for (Piece p : playerNumber.getPieces() ) {
			int idx = p.getPathIndex();
			if( idx + diceValue < board.trackLength) {
				Pair<Piece, Integer> move = new Pair<>();
				move.first = p;
				move.second = idx + diceValue;
				possibleMoves.add(move);
			}
		}
		return possibleMoves;
	}

}
