import java.util.Random;

public class Dice {
	private boolean _hasUsedFirst;
	private boolean _hasUsedSecond;
	
	private int firstDie;
	private int secondDie;
	

	private Random rng;
	
	Dice() {
		this._hasUsedFirst = false;
		this._hasUsedSecond = false;
		this.firstDie = 1;
		this.secondDie = 1;
		this.rng = new Random();
	}
	
	public void roll() {
		this._hasUsedFirst = false;
		this._hasUsedSecond = false;
		this.firstDie = this.rng.nextInt(6) + 1; // [0,6) + 1 = [1,7) = [1,6], uniformely distributed
		this.secondDie = this.rng.nextInt(6) + 1;
	}
	
	public int getFirst() {
		return firstDie;
	}
	
	public int getSecond() {
		return secondDie;
	}
	
	public boolean hasUsedFirst() {
		return _hasUsedFirst;
	}
	
	public boolean hasUsedSecond() {
		return _hasUsedSecond;
	}
	
	public int useFirst() {
		this._hasUsedFirst = true;
		return this.firstDie;
	}
	
	public int useSecond() {
		this._hasUsedSecond = true;
		return this.secondDie;
	}
}
