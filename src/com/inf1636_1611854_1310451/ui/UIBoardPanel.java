package com.inf1636_1611854_1310451.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.inf1636_1611854_1310451.game.BoardSquare;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.Piece;
import com.inf1636_1611854_1310451.game.PieceMovement;
import com.inf1636_1611854_1310451.util.Subject;
import com.inf1636_1611854_1310451.util.Vector2D;

import java.util.*;
public class UIBoardPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 840;
    private int squareSideLength = 45;
    
    private Vector<BoardSquare> boardSquaresToDraw = new Vector<BoardSquare>();
    private Vector<PieceMovement> PieceMovement = new Vector<PieceMovement>();
    public Subject<BoardSquare> onBoardSquareClick = new Subject<BoardSquare>();
	
	public UIBoardPanel()
    {
        this.addMouseListener(this);
	}
	
    public void updateBoardSquares(Vector<BoardSquare> vec) {
    	this.boardSquaresToDraw = vec;
    	this.repaint();
    }
    
    public void updatePossibleMovements(Vector<PieceMovement> vec) {
    	this.PieceMovement = vec;
    	this.repaint();
    }
    
    private void drawBackground(Graphics2D g2D) {
    	int redTriangleX[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 6 * this.squareSideLength};
        int redTriangleY[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        g2D.setColor(Color.RED);
        g2D.fillRect(this.squareSideLength * 0, this.squareSideLength * 0, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.fillPolygon(redTriangleX, redTriangleY, 3);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(this.squareSideLength * 0, this.squareSideLength * 0, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.drawPolygon(redTriangleX, redTriangleY, 3);
        
        int greenTriangleX[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        int greenTriangleY[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 6 * this.squareSideLength};
        g2D.setColor(Color.GREEN);
        g2D.fillRect(this.squareSideLength * 9, this.squareSideLength * 0, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.fillPolygon(greenTriangleX, greenTriangleY, 3);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(this.squareSideLength * 9, this.squareSideLength * 0, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.drawPolygon(greenTriangleX, greenTriangleY, 3);
        
        int blueTriangleX[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        int blueTriangleY[] = {9 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        g2D.setColor(Color.BLUE);
        g2D.fillRect(this.squareSideLength * 0, this.squareSideLength * 9, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.fillPolygon(blueTriangleX, blueTriangleY, 3);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(this.squareSideLength * 0, this.squareSideLength * 9, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.drawPolygon(blueTriangleX, blueTriangleY, 3);
        
        int yellowTriangleX[] = {9 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        int yellowTriangleY[] = {6 * this.squareSideLength, (int) (7.5f * this.squareSideLength), 9 * this.squareSideLength};
        g2D.setColor(Color.YELLOW);
        g2D.fillRect(this.squareSideLength * 9, this.squareSideLength * 9, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.fillPolygon(yellowTriangleX, yellowTriangleY, 3);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(this.squareSideLength * 9, this.squareSideLength * 9, this.squareSideLength * 6, this.squareSideLength * 6);
        g2D.drawPolygon(yellowTriangleX, yellowTriangleY, 3);
    }
    
    private void drawBoardSquareBasis(Graphics2D g2D, BoardSquare boardSquare) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	Color color;
    	if(boardSquare.isShelter()) {
    		color = Color.black;
    	} else {
    		color = boardSquare.getColor();
    	}
        g2D.setColor(color);
        g2D.fillRect(	topLeftPosition.x * this.squareSideLength + 1,
        				topLeftPosition.y * this.squareSideLength + 1,
        				this.squareSideLength - 1,
        				this.squareSideLength - 1);
        if(!boardSquare.isTerminal()) {
            g2D.setColor(Color.BLACK);
            g2D.drawRect(	topLeftPosition.x * this.squareSideLength,
            				topLeftPosition.y * this.squareSideLength,
            				this.squareSideLength,
            				this.squareSideLength);
        }
    }
    
    private void drawBoardSquareWithOnePiece(Graphics2D g2D, BoardSquare boardSquare, Piece piece) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	Color color = piece.getPlayer().getColor();
        g2D.setColor(color);
        g2D.fillOval(topLeftPosition.x * this.squareSideLength, topLeftPosition.y * this.squareSideLength, this.squareSideLength, this.squareSideLength);
        g2D.setColor(Color.BLACK);
        g2D.drawOval(topLeftPosition.x * this.squareSideLength, topLeftPosition.y * this.squareSideLength, this.squareSideLength, this.squareSideLength);
		if(Ludo.getInstance().isPieceSelected(piece)) {
            g2D.setColor(Color.WHITE);
            g2D.drawOval(topLeftPosition.x * this.squareSideLength + this.squareSideLength/4, topLeftPosition.y * this.squareSideLength + this.squareSideLength/4, this.squareSideLength/2, this.squareSideLength/2);
		}
    }
    
    private void drawBoardSquareWithTwoPieces(Graphics2D g2D, BoardSquare boardSquare, Piece piece1, Piece piece2) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	Color innerColor = piece1.getPlayer().getColor();
    	Color outerColor = piece2.getPlayer().getColor();
    	
    	if(piece1.getPlayer() == piece2.getPlayer()) {
    		outerColor = Color.WHITE;
    	}
    	
        g2D.setColor(outerColor);
        g2D.fillOval(topLeftPosition.x * this.squareSideLength, topLeftPosition.y * this.squareSideLength, this.squareSideLength, this.squareSideLength);
        

        g2D.setColor(innerColor);
        g2D.fillOval(topLeftPosition.x * this.squareSideLength + this.squareSideLength/8, topLeftPosition.y * this.squareSideLength + this.squareSideLength/8, 3*this.squareSideLength/4, 3*this.squareSideLength/4);
        
        g2D.setColor(Color.BLACK);
        g2D.drawOval(topLeftPosition.x * this.squareSideLength, topLeftPosition.y * this.squareSideLength, this.squareSideLength, this.squareSideLength);
        
		if(Ludo.getInstance().isPieceSelected(piece1) || Ludo.getInstance().isPieceSelected(piece2)) {
            g2D.setColor(Color.WHITE);
            g2D.drawOval(topLeftPosition.x * this.squareSideLength + this.squareSideLength/4, topLeftPosition.y * this.squareSideLength + this.squareSideLength/4, this.squareSideLength/2, this.squareSideLength/2);
		}
    }
    
    private void drawBoardSquarePieces(Graphics2D g2D, BoardSquare boardSquare) {
    	Vector<Piece> pieces = boardSquare.getPieces();
    	if(pieces.size() == 1) {
        	this.drawBoardSquareWithOnePiece(g2D, boardSquare, pieces.get(0));
    	}
    	if(pieces.size() == 2) {
        	this.drawBoardSquareWithTwoPieces(g2D, boardSquare, pieces.get(0), pieces.get(1));
    	}
    }
    
    private void drawBoardSquares(Graphics2D g2D) {
    	this.drawBackground(g2D);
        for(BoardSquare boardSquare: this.boardSquaresToDraw) {
        	this.drawBoardSquareBasis(g2D, boardSquare);
        	this.drawBoardSquarePieces(g2D, boardSquare);
        }
    }

    private void drawPieceMovements(Graphics2D g2D) {
        for(PieceMovement p: this.PieceMovement) {
        	Vector2D topLeftPosition = p.boardSquare.getTopLeftPosition();
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x * this.squareSideLength + 2, topLeftPosition.y * this.squareSideLength + 2, this.squareSideLength - 4, this.squareSideLength - 4);
            g2D.setColor(Color.WHITE);
            g2D.drawRect(topLeftPosition.x * this.squareSideLength + 1, topLeftPosition.y * this.squareSideLength + 1, this.squareSideLength - 2, this.squareSideLength - 2);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x * this.squareSideLength, topLeftPosition.y * this.squareSideLength, this.squareSideLength, this.squareSideLength);
            if(p.boardSquare.isShelter()) {
            	g2D.setColor(Color.WHITE);
            } else {
            	g2D.setColor(Color.BLACK);
            }
            g2D.drawString(p.diceRoll.toString(), topLeftPosition.x * this.squareSideLength + 3, topLeftPosition.y * this.squareSideLength + this.squareSideLength - 3);
        }
    }

    public void paint(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        
        this.drawBoardSquares(g2D);
        // this.drawPieces(g2D);
        this.drawPieceMovements(g2D);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
		int y = e.getY();
		for(BoardSquare b: this.boardSquaresToDraw) {
			if(	x > b.topLeftPosition.x * this.squareSideLength &&
				x < b.topLeftPosition.x * this.squareSideLength + this.squareSideLength &&
				y > b.topLeftPosition.y * this.squareSideLength &&
				y < b.topLeftPosition.y * this.squareSideLength + this.squareSideLength ) {
				Ludo.getInstance().clickBoardSquare(b);
			}
		}
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
