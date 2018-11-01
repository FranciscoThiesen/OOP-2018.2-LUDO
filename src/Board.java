import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.tools.Tool;
import java.util.*;

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

        ArrayList<BoardSquare> redSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> blueSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> greenSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> yellowSquares = new ArrayList<BoardSquare>();

        redSquares.add( new BoardSquare(new Coordinate2D(0, 0), 240, Color.RED ) );
        blueSquares.add( new BoardSquare(new Coordinate2D(0, 360), 240, Color.BLUE ) );
        greenSquares.add( new BoardSquare(new Coordinate2D(360, 0), 240, Color.GREEN ) );
        yellowSquares.add( new BoardSquare(new Coordinate2D(360, 360), 240, Color.YELLOW) );

        redSquares.add( new BoardSquare(new Coordinate2D(40, 240), 40, Color.RED) );
        blueSquares.add( new BoardSquare(new Coordinate2D(240, 520), 40, Color.BLUE) );
        greenSquares.add( new BoardSquare(new Coordinate2D(320, 40), 40, Color.GREEN) );
        yellowSquares.add( new BoardSquare(new Coordinate2D(520, 320), 40, Color.YELLOW) );

        for(int i = 0; i < 5; i++) {
            redSquares.add( new BoardSquare(new Coordinate2D((i + 1) * 40, 280), 40, Color.RED) );
            blueSquares.add( new BoardSquare(new Coordinate2D(280, 560 - ( (i + 1) * 40) ), 40, Color.BLUE) );
            greenSquares.add( new BoardSquare(new Coordinate2D(280, (i + 1) * 40), 40, Color.GREEN) );
            yellowSquares.add( new BoardSquare(new Coordinate2D(560 - ( ( i + 1) * 40), 280), 40, Color.YELLOW) );
        }

        // approach diferente
        g2D.setStroke(new BasicStroke(1) );
        g2D.setColor(Color.BLACK);
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                g2D.drawRect(i * 40, j * 40, 40, 40);
            }
        }

        g2D.setStroke(new BasicStroke(2) );

        for(BoardSquare b : redSquares) {
            int x = b.topLeftPosition.x;
            int y = b.topLeftPosition.y;
            int len = b.sideLength;
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x, y, len, len);
            g2D.setColor(Color.RED);
            g2D.fillRect(x, y, len, len);
        }

        for(BoardSquare b : blueSquares) {
            int x = b.topLeftPosition.x;
            int y = b.topLeftPosition.y;
            int len = b.sideLength;
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x, y, len, len);
            g2D.setColor(Color.BLUE);
            g2D.fillRect(x, y, len, len);
        }

        for(BoardSquare b : greenSquares) {
            int x = b.topLeftPosition.x;
            int y = b.topLeftPosition.y;
            int len = b.sideLength;
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x, y, len, len);
            g2D.setColor(Color.GREEN);
            g2D.fillRect(x, y, len, len);
        }

        for(BoardSquare b : yellowSquares) {
            int x = b.topLeftPosition.x;
            int y = b.topLeftPosition.y;
            int len = b.sideLength;
            g2D.setColor(Color.BLACK);
            g2D.drawRect(x, y, len, len);
            g2D.setColor(Color.YELLOW);
            g2D.fillRect(x, y, len, len);
        }

    }

}
