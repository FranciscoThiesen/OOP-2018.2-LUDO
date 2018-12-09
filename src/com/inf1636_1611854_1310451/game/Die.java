package com.inf1636_1611854_1310451.game;
import java.util.Random;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.inf1636_1611854_1310451.util.Savable;
import com.inf1636_1611854_1310451.util.Subject;

public class Die implements Savable {

	private Random rng;
	private int value;
	private boolean _hasBeenUsed;

	public Subject<Die> onStateChange = new Subject<>();

	public Die() {
		this._hasBeenUsed = true;
		this.value = 1;
		this.rng = new Random();
		this.onStateChange = new Subject<>();
	}
	
	@SuppressWarnings("unchecked")
	public String saveStateToString() {
		JSONObject obj = new JSONObject();
		obj.put("value", this.value);
		obj.put("_hasBeenUsed", this._hasBeenUsed);
		return obj.toJSONString();
	}
	
	public void loadStateFromString(String str) {
	      JSONParser parser = new JSONParser();
	      try{
		      JSONObject obj = (JSONObject) parser.parse(str);
		      this.value = (Integer) obj.get("value");
		      this._hasBeenUsed = (Boolean) obj.get("_hasBeenUsed");
	      }catch(ParseException pe){
	          System.out.println("Error loading Die state from JSON.");
	       }
	}
	
	public void roll() {
		this._hasBeenUsed = false;
		this.value = this.rng.nextInt(6) + 1;  // [0,6) + 1 = [1,7) = [1,6], uniformely distributed
		this.onStateChange.notifyAllObservers(this);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean hasBeenUsed() {
		return this._hasBeenUsed;
	}
	
	public int use() {
		this._hasBeenUsed = true;
		this.onStateChange.notifyAllObservers(this);
		return this.value;
	}

	public Vector<Integer> availableDieValue() {
		Vector<Integer> availableValues = new Vector<>();
		if( this.hasBeenUsed() == false ) availableValues.add( this.getValue() );
		return availableValues;
	}

}
