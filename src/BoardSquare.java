import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BoardSquare {
    public Vector2D topLeftPosition;
    public int sideLength;

    public BoardSquare() {
        this.topLeftPosition = new Vector2D(0, 0);
        this.sideLength = 10;
    }
    
    public BoardSquare(Vector2D topLeftPosition, int sideLength) {
        this.setPosition(topLeftPosition);
        this.setSideLength(sideLength);
    }

    public boolean isInside(Vector2D coord) {
        double minY = topLeftPosition.y - sideLength;
        double maxX = topLeftPosition.x + sideLength;
        return (coord.x >= topLeftPosition.x && coord.x <= maxX && coord.y <= topLeftPosition.y && coord.y >= minY);
    }

    public Rectangle2D toRect() {
        Rectangle2D r2D = new Rectangle2D.Float( (float) this.topLeftPosition.x, (float) this.topLeftPosition.y, (float) this.sideLength, (float) this.sideLength);
        return r2D;
    }
    
    public void setPosition(Vector2D topLeftPosition) {
        this.topLeftPosition = topLeftPosition;
    }
    
    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }
}
