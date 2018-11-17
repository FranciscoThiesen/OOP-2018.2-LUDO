import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class UIBoardPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 840;

    private Vector2D piecePosition = new Vector2D(300, 300);
    private int radius = 50;
    private boolean isPieceSelected = false;
    
    private Vector<BoardSquare> boardSquaresToDraw;
	
	public UIBoardPanel() {
        this.addMouseListener(this);
	}
	
    public void updateBoardSquares(Vector<BoardSquare> vec) {
    	this.boardSquaresToDraw = vec;
    }
    
    private void drawBoardSquares(Graphics2D g2D) {
        for(int i=0; i<this.boardSquaresToDraw.size(); i++) {
        	BoardSquare boardSquare = this.boardSquaresToDraw.get(i);
        	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
        	int sideLength = boardSquare.getSideLength();
            g2D.setColor(boardSquare.getColor());
            g2D.fillRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        
        this.drawBoardSquares(g2D);
        
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
