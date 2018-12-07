package com.inf1636_1611854_1310451.util;
import java.util.List;
import java.util.function.Consumer;
import java.util.ArrayList;

public class Subject<T> {
	
	private List<Consumer<T>> observers;
	
	public Subject() {
		this.observers = new ArrayList<Consumer<T>>();
	}

	public void attach(Consumer<T> func) {
		this.observers.add(func);
	}
	
	public void notifyAllObservers(T value) {
      for (Consumer<T> consumer : this.observers) {
    	  consumer.accept(value);
       }
	}
}
