package com.inf1636_1611854_1310451.util;
import java.util.ArrayList;
import java.util.List;

public class SubjectVoid {
	
	private List<Runnable> observers;
	
	public SubjectVoid() {
		this.observers = new ArrayList<Runnable>();
	}

	public void attach(Runnable func) {
		this.observers.add(func);
	}
	
	public void notifyAllObservers() {
      for (Runnable consumer : this.observers) {
    	  consumer.run();
       }
	}
}
