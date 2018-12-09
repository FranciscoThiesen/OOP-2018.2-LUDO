package com.inf1636_1611854_1310451.util;

import org.json.simple.JSONObject;

public interface Savable {
	public JSONObject saveStateToJSON();
	public void loadStateFromJSON(JSONObject obj);
}
