import java.util.Vector;

public class Dice {
	private Die first;
	private Die second;

	public Dice() {
		this.first = new Die();
		this.second = new Die();
	}
	
	public void roll() {
		this.first.roll();
		this.second.roll();
	}
	
	public int getFirst() {
		return this.first.getValue();
	}
	
	public int getSecond() {
		return this.second.getValue();
	}
	
	public boolean hasUsedFirst() {
		return this.first.hasBeenUsed();
	}
	
	public boolean hasUsedSecond() {
		return this.second.hasBeenUsed();
	}
	
	public int useFirst() {
		return this.first.use();
	}
	
	public int useSecond() {
		return this.second.use();
	}
	
	public Vector<Integer> availableDiceValues() {
		Vector<Integer> vec = new Vector<Integer>();
		if(!this.hasUsedFirst()) {
			vec.add(this.getFirst());
		}
		if(!this.hasUsedSecond()) {
			vec.add(this.getSecond());
		}
		return vec;
	}
}
