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
		this.players = new Vector<Player>();
		this.players.add(new Player(Color.RED));
		this.players.add(new Player(Color.GREEN));
		this.players.add(new Player(Color.BLUE));
		this.players.add(new Player(Color.YELLOW));
		this.currentPlayerIndex = 0;
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
}
