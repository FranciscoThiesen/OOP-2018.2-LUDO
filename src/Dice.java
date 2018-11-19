import java.util.Vector;

public class Dice {
	private Die first;
	private Die second;
	
	public Subject<Dice> onStateChange = new Subject<Dice>();

	public Dice() {
		this.first = new Die();
		this.second = new Die();
	}
	
	public void roll() {
		this.first.roll();
		this.second.roll();
		this.onStateChange.notifyAllObservers(this);
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
		int v = this.first.use();
		this.onStateChange.notifyAllObservers(this);
		return v;
	}
	
	public int useSecond() {
		int v = this.second.use();
		this.onStateChange.notifyAllObservers(this);
		return v;
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
	
	public void useRoll(int roll) {
		if(roll == this.getFirst() && !this.hasUsedFirst()) {
			this.useFirst();
			return;
		}
		if(roll == this.getSecond() && !this.hasUsedSecond()) {
			this.useSecond();
			return;
		}
	}
}
