import java.util.Random;

public class Die {

	private Random rng;
	private int value;
	private boolean _hasBeenUsed;
	
	public Subject<Integer> onRoll;
	
	public Die() {
		this._hasBeenUsed = false;
		this.value = 1;
		this.rng = new Random();
		this.onRoll = new Subject<Integer>();
	}
	
	public void roll() {
		this._hasBeenUsed = false;
		this.value = this.rng.nextInt(6) + 1;  // [0,6) + 1 = [1,7) = [1,6], uniformely distributed
		this.onRoll.notifyAllObservers(this.value);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean hasBeenUsed() {
		return this._hasBeenUsed;
	}
	
	public int use() {
		this._hasBeenUsed = true;
		return this.value;
	}

}
