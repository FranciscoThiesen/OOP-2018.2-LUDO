public class Ludo {
	
	private Board board;
	private Dice dice;
	
	Ludo() {
		this.board = new Board();
		this.dice = new Dice();
	}
	
    public void run() {
        this.board.setVisible(true);
    }
}
