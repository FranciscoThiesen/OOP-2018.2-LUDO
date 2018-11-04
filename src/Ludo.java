import java.awt.Color;
import java.util.Vector;

public class Ludo {
	
	private Board board;
	private Dice dice;
	private Vector<Player> players;
	private int currentPlayerIndex;
	
	public Ludo() {
		this.board = new Board();
		this.dice = new Dice();
		this.players.add(new Player(Color.RED));
		this.players.add(new Player(Color.GREEN));
		this.players.add(new Player(Color.BLUE));
		this.players.add(new Player(Color.YELLOW));
		this.currentPlayerIndex = 0;
	}
	
    public void run() {
        this.board.setVisible(true);
    }
}
