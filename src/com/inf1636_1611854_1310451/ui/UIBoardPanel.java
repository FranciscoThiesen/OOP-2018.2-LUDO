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
    
    private void drawBoardSquareBasis(Graphics2D g2D, BoardSquare boardSquare) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	int sideLength = boardSquare.getSideLength();
    	Color color;
    	if(boardSquare.isShelter()) {
    		color = Color.black;
    	} else {
    		color = boardSquare.getColor();
    	}
        g2D.setColor(color);
        g2D.fillRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);	
    }
    
    private void drawBoardSquareWithOnePiece(Graphics2D g2D, BoardSquare boardSquare, Piece piece) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	int sideLength = boardSquare.getSideLength();
    	Color color = piece.getPlayer().getColor();
        g2D.setColor(color);
        g2D.fillOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
        g2D.setColor(Color.BLACK);
        g2D.drawOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
		if(Ludo.getInstance().isPieceSelected(piece)) {
            g2D.setColor(Color.WHITE);
            g2D.drawOval(topLeftPosition.x + sideLength/4, topLeftPosition.y + sideLength/4, sideLength/2, sideLength/2);
		}
    }
    
    private void drawBoardSquareWithTwoPieces(Graphics2D g2D, BoardSquare boardSquare, Piece piece1, Piece piece2) {
    	Vector2D topLeftPosition = boardSquare.getTopLeftPosition();
    	int sideLength = boardSquare.getSideLength();
    	Color innerColor = piece1.getPlayer().getColor();
    	Color outerColor = piece2.getPlayer().getColor();
    	
    	if(piece1.getPlayer() == piece2.getPlayer()) {
    		outerColor = Color.WHITE;
    	}
    	
        g2D.setColor(outerColor);
        g2D.fillOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
        

        g2D.setColor(innerColor);
        g2D.fillOval(topLeftPosition.x + sideLength/8, topLeftPosition.y + sideLength/8, 3*sideLength/4, 3*sideLength/4);
        
        g2D.setColor(Color.BLACK);
        g2D.drawOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
        
		if(Ludo.getInstance().isPieceSelected(piece1) || Ludo.getInstance().isPieceSelected(piece2)) {
            g2D.setColor(Color.WHITE);
            g2D.drawOval(topLeftPosition.x + sideLength/4, topLeftPosition.y + sideLength/4, sideLength/2, sideLength/2);
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
        for(BoardSquare boardSquare: this.boardSquaresToDraw) {
        	this.drawBoardSquareBasis(g2D, boardSquare);
        	this.drawBoardSquarePieces(g2D, boardSquare);
        }
    }

    private void drawPieceMovements(Graphics2D g2D) {
        for(PieceMovement p: this.PieceMovement) {
        	Vector2D topLeftPosition = p.boardSquare.getTopLeftPosition();
        	int sideLength = p.boardSquare.getSideLength();
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x + 2, topLeftPosition.y + 2, sideLength - 4, sideLength - 4);
            g2D.setColor(Color.WHITE);
            g2D.drawRect(topLeftPosition.x + 1, topLeftPosition.y + 1, sideLength - 2, sideLength - 2);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
            if(p.boardSquare.isShelter()) {
            	g2D.setColor(Color.WHITE);
            } else {
            	g2D.setColor(Color.BLACK);
            }
            g2D.drawString(p.diceRoll.toString(), topLeftPosition.x + 3, topLeftPosition.y + sideLength - 3);
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
			if(	x > b.topLeftPosition.x &&
				x < b.topLeftPosition.x + b.sideLength &&
				y > b.topLeftPosition.y &&
				y < b.topLeftPosition.y + b.sideLength ) {
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
