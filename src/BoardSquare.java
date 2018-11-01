import java.awt.*;

public class BoardSquare {
    public Coordinate2D topLeftPosition;
    public int sideLength;
    public Color squareColor;

    public BoardSquare(Coordinate2D x, int y, Color c) {
        this.topLeftPosition = x;
        this.sideLength = y;
        this.squareColor = c;
    }

    public boolean isInside(Coordinate2D coord) {
        double minY = topLeftPosition.y - sideLength;
        double maxX = topLeftPosition.x + sideLength;
        return (coord.x >= topLeftPosition.x && coord.x <= maxX && coord.y <= topLeftPosition.y && coord.y >= minY);
    }
}
