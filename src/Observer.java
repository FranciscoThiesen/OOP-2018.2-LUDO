
public abstract class Observer<T> {
	public Subject<T> subject;
	public abstract void update(T value);
}
