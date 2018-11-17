import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class UIHandler extends JFrame implements MouseListener  {

	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 840;

    
    private Vector2D piecePosition = new Vector2D(300, 300);
    private int radius = 50;
    private boolean isPieceSelected = false;
    

    public UIHandler() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = screenWidth / 2 - DEFAULT_WIDTH / 2;
        int y = screenHeight / 2 - DEFAULT_HEIGHT / 2;

        this.setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addMouseListener(this);

    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        
        Graphics2D g2D = (Graphics2D) g;

        ArrayList<BoardSquare> redSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> blueSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> greenSquares = new ArrayList<BoardSquare>();
        ArrayList<BoardSquare> yellowSquares = new ArrayList<BoardSquare>();

        redSquares.add( new BoardSquare(new Vector2D(0, 0), 240, Color.RED ) );
        blueSquares.add( new BoardSquare(new Vector2D(0, 360), 240, Color.BLUE ) );
        greenSquares.add( new BoardSquare(new Vector2D(360, 0), 240, Color.GREEN ) );
        yellowSquares.add( new BoardSquare(new Vector2D(360, 360), 240, Color.YELLOW) );

        redSquares.add( new BoardSquare(new Vector2D(40, 240), 40, Color.RED) );
        blueSquares.add( new BoardSquare(new Vector2D(240, 520), 40, Color.BLUE) );
        greenSquares.add( new BoardSquare(new Vector2D(320, 40), 40, Color.GREEN) );
        yellowSquares.add( new BoardSquare(new Vector2D(520, 320), 40, Color.YELLOW) );

        for(int i = 0; i < 5; i++) {
            redSquares.add( new BoardSquare(new Vector2D((i + 1) * 40, 280), 40, Color.RED) );
            blueSquares.add( new BoardSquare(new Vector2D(280, 560 - ( (i + 1) * 40) ), 40, Color.BLUE) );
            greenSquares.add( new BoardSquare(new Vector2D(280, (i + 1) * 40), 40, Color.GREEN) );
            yellowSquares.add( new BoardSquare(new Vector2D(560 - ( ( i + 1) * 40), 280), 40, Color.YELLOW) );
        }

        // approach diferente
        g2D.setStroke(new BasicStroke(1) );
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                g2D.setColor(Color.WHITE);
                g2D.fillRect(i * 40, j * 40, 40, 40);
                g2D.setColor(Color.BLACK);
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
        
        // draw piece
        g2D.setColor(Color.BLACK);
        g2D.drawOval(this.piecePosition.x, this.piecePosition.y, this.radius, this.radius);
		if(this.isPieceSelected) {
	        g2D.setColor(Color.PINK);
		} else {
	        g2D.setColor(Color.CYAN);
		}
        g2D.fillOval(this.piecePosition.x, this.piecePosition.y, this.radius, this.radius);

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
		int y = e.getY();
		Vector2D mousePos = new Vector2D(x, y);
		System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
		
		if(this.isPieceSelected) {
			this.isPieceSelected = false;
			this.piecePosition = new Vector2D(x, y);
		} else if(mousePos.subtract(this.piecePosition).len2() < this.radius * this.radius) {
			System.out.println("inside");
			this.isPieceSelected = true;
		} else {
			System.out.println("out");
		}
		this.repaint();
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
