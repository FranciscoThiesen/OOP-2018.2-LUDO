import java.util.List;
import java.util.ArrayList;

public class Subject<T> {
	
	private List<Observer<T>> observers;
	
	public Subject() {
		this.observers = new ArrayList<Observer<T>>();
	}

	public void attach(Observer<T> observer) {
		this.observers.add(observer);
	}
	
	public void notifyAllObservers(T value) {
      for (Observer<T> observer : observers) {
          observer.update(value);
       }
	}
}
