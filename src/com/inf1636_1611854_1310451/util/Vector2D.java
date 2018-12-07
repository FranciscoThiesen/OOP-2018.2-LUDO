package com.inf1636_1611854_1310451.util;
public class Vector2D {
    public int x, y;

    public Vector2D(int v1, int v2) {
        this.x = v1;
        this.y = v2;
    }
    
    public Vector2D subtract(Vector2D other) {
    	return new Vector2D(this.x - other.x, this.y - other.y);
    }
    
    public float len2() {
    	return (this.x * this.x) + (this.y * this.y);
    }
}
