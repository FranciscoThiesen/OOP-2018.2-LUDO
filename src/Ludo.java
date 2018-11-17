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
		ArrayList<Piece> pecasNoQuadrado = new ArrayList<Piece>();
		for( Player x : players)
		{
			Vector<Piece> pieceVector = x.getPieces();
			for( Piece p : pieceVector)
			{
				Vector<Integer> track = p.getPieceTrack();
				int trackIndex = p.getPathIndex();
				Integer b = track.get(trackIndex);
				pecasNoQuadrado.add( p );
			}
		}
		return pecasNoQuadrado;
	}
}
