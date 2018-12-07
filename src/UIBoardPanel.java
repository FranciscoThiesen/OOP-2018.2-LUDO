import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.*;
import java.io.IOException;
public class UIBoardPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 600;
    public static final int DEFAULT_HEIGHT = 840;
    
    private Vector<BoardSquare> boardSquaresToDraw = new Vector<BoardSquare>();
    private Vector<PiecePositioningInfo> pieceInfoToDraw = new Vector<PiecePositioningInfo>();
    private Vector<PossiblePieceMovement> possiblePieceMovement = new Vector<PossiblePieceMovement>();
    private Die die = new Die();
    private BufferedImage[] diePictures = new BufferedImage[6];
    public Subject<BoardSquare> onBoardSquareClick = new Subject<BoardSquare>();
	
	public UIBoardPanel()
    {
        String fileNames[] = {"out/production/Ludo/Dado1.png", "out/production/Ludo/Dado2.png", "out/production/Ludo/Dado3.png", "out/production/Ludo/Dado4.png", "out/production/Ludo/Dado5.png", "out/production/Ludo/Dado6.png"};
        for(int i = 0; i < 6; ++i) {
            try {
                this.diePictures[i] = ImageIO.read(new File(fileNames[i]));
                System.out.print("Inseri uma das faces\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.addMouseListener(this);
	}
	
    public void updateBoardSquares(Vector<BoardSquare> vec) {
    	this.boardSquaresToDraw = vec;
    	this.repaint();
    }
    
    public void updatePiecesInfo(Vector<PiecePositioningInfo> vec) {
    	this.pieceInfoToDraw = vec;
    	this.repaint();
    }
    
    public void updatePossibleMovements(Vector<PossiblePieceMovement> vec) {
    	this.possiblePieceMovement = vec;
    	this.repaint();
    }

    public void updateDie( Die die ) {
	    this.die = die;
	    this.repaint();
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

    private void drawPossiblePieceMovements(Graphics2D g2D) {
        for(PossiblePieceMovement p: this.possiblePieceMovement) {
        	Vector2D topLeftPosition = p.boardSquare.getTopLeftPosition();
        	int sideLength = p.boardSquare.getSideLength();
            g2D.setColor(Color.PINK);
            g2D.fillRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
            g2D.drawString(p.diceRoll.toString(), topLeftPosition.x + 1, topLeftPosition.y + sideLength - 1);
        }
    }

    private void drawPieces(Graphics2D g2D) {
    	for(PiecePositioningInfo p: this.pieceInfoToDraw) {
        	Vector2D topLeftPosition = p.position.getTopLeftPosition();
        	int sideLength = p.position.getSideLength();
        	Color c = p.player.getColor();
            g2D.setColor(c);
            g2D.fillOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
            g2D.setColor(Color.BLACK);
            g2D.drawOval(topLeftPosition.x, topLeftPosition.y, sideLength, sideLength);
    		if(Ludo.getInstance().isPieceSelected(p.piece)) {
                g2D.setColor(Color.WHITE);
                g2D.drawOval(topLeftPosition.x + sideLength/4, topLeftPosition.y + sideLength/4, sideLength/2, sideLength/2);
    		}
    	}
    }

    private void drawDie(Graphics2D g2D) {
	    g2D.drawImage( this.diePictures[ this.die.getValue() - 1], 25, 25, this);
	}

    public void paint(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        
        this.drawBoardSquares(g2D);
        this.drawPieces(g2D);
        this.drawPossiblePieceMovements(g2D);
        this.drawDie(g2D);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
		int y = e.getY();
		Vector2D mousePos = new Vector2D(x, y);
		for(BoardSquare b: this.boardSquaresToDraw) {
			if(	x > b.topLeftPosition.x &&
				x < b.topLeftPosition.x + b.sideLength &&
				y > b.topLeftPosition.y &&
				y < b.topLeftPosition.y + b.sideLength ) {
				this.onBoardSquareClick.notifyAllObservers(b);
			}
		}
    }
    
    public void onPieceSelect(Piece p) {
    	this.updatePossibleMovements(Ludo.getInstance().getPlacesGivenPieceCanMove(p));
    	this.repaint();
    }
    
    public void onPieceUnSelect(Piece p) {
    	this.updatePossibleMovements(new Vector<PossiblePieceMovement>());
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
