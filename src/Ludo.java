import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.ArrayList;

public class Ludo {
	
	private Board board;
	private Dice dice;
	private Vector<Player> players;
	private int currentPlayerIndex;

	public Subject<Player> onPlayerChange = new Subject<Player>();
	public Subject<Pair<Integer, Integer>> onDiceRoll = new Subject<Pair<Integer, Integer>>();
	public SubjectVoid onTurnComplete = new SubjectVoid();
	
	public Ludo() {
		this.board = new Board();
		this.dice = new Dice();
		this.players = new Vector<Player>();
		this.players.add(new Player(Color.RED, board.redPiecesTracks, "RED"));
		this.players.add(new Player(Color.GREEN, board.greenPiecesTracks, "GREEN"));
		this.players.add(new Player(Color.BLUE, board.bluePiecesTracks, "BLUE"));
		this.players.add(new Player(Color.YELLOW, board.yellowPiecesTracks, "YELLOW"));
		this.currentPlayerIndex = 0;
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
        this.dice.roll();
        Pair<Integer, Integer> notification = new Pair<Integer, Integer>(this.dice.getFirst(), this.dice.getSecond());
        this.onDiceRoll.notifyAllObservers(notification);
        this.onTurnComplete.notifyAllObservers();
	}
	
    public void run() {        

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
