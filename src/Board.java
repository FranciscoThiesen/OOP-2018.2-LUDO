import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.tools.Tool;

public class Board extends JFrame {

    public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 840;

    public Board() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = screenWidth / 2 - DEFAULT_WIDTH / 2;
        int y = screenHeight / 2 - DEFAULT_HEIGHT / 2;

        setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        BoardSquare[] sanctuarySquares = new BoardSquare[4];
        Coordinate2D[] sanctuaryTopLeftCoords = new Coordinate2D[4];
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.yellow};
        for(int i = 0; i < 4; i++ ) {
            if(i == 0) sanctuaryTopLeftCoords[i] = new Coordinate2D(0, 0);
            else if(i == 1) sanctuaryTopLeftCoords[i] = new Coordinate2D(0, 360);
            else if(i == 2) sanctuaryTopLeftCoords[i] = new Coordinate2D(360, 0);
            else sanctuaryTopLeftCoords[i] = new Coordinate2D(360, 360);
            sanctuarySquares[i] = new BoardSquare(sanctuaryTopLeftCoords[i], 240, colors[i]);
        }


        g2D.setStroke(new BasicStroke(1) );
        for(int i = 0; i < 4; i++) {
            g2D.setColor(Color.BLACK);
            g2D.drawRect(sanctuarySquares[i].topLeftPosition.x, sanctuarySquares[i].topLeftPosition.y, sanctuarySquares[i].sideLength, sanctuarySquares[i].sideLength);
            g2D.setColor(sanctuarySquares[i].squareColor);
            g2D.fillRect(sanctuarySquares[i].topLeftPosition.x, sanctuarySquares[i].topLeftPosition.y, sanctuarySquares[i].sideLength, sanctuarySquares[i].sideLength);
        }
        //g2D.setColor(Color.BLACK);

        //g2D.fillRect(500, 0, 100, 100);

    }

}
