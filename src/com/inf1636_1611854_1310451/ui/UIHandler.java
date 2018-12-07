package com.inf1636_1611854_1310451.ui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.inf1636_1611854_1310451.game.BoardSquare;
import com.inf1636_1611854_1310451.game.Die;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.Piece;
import com.inf1636_1611854_1310451.game.PiecePositioningInfo;
import com.inf1636_1611854_1310451.game.Player;
import com.inf1636_1611854_1310451.game.PossiblePieceMovement;
import com.inf1636_1611854_1310451.util.Subject;
import com.inf1636_1611854_1310451.util.SubjectVoid;

import java.util.*;

public class UIHandler extends JFrame {

	private static final long serialVersionUID = -3395052687382008316L;
	public static final int DEFAULT_WIDTH  = 1000;
    public static final int DEFAULT_HEIGHT = 800;

    private JSplitPane mainPanel;
    
    private UIBoardPanel boardPanel;
    private UIControlsPanel controlsPanel;
    private UIAuxiliarPanel auxiliarPanel;
	public SubjectVoid onDieRollButtonClick = new SubjectVoid();
	public SubjectVoid onNextTurnButtonClick = new SubjectVoid();
    public Subject<BoardSquare> onBoardSquareClick = new Subject<BoardSquare>();
    
    public UIHandler() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = screenWidth / 2 - DEFAULT_WIDTH / 2;
        int y = screenHeight / 2 - DEFAULT_HEIGHT / 2;
    
        this.setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        
        this.boardPanel = new UIBoardPanel();
        this.boardPanel.onBoardSquareClick.attach((BoardSquare b) -> { this.onBoardSquareClick.notifyAllObservers(b); });
        this.controlsPanel = new UIControlsPanel();
        this.controlsPanel.onDieRollButtonClick.attach(() -> { this.onDieRollButtonClick.notifyAllObservers(); });
        this.controlsPanel.onNextTurnButtonClick.attach(() -> { this.onNextTurnButtonClick.notifyAllObservers(); });
        
        // -------------------------
        
        this.mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.controlsPanel, this.boardPanel);
        this.mainPanel.setOneTouchExpandable(true);
        this.mainPanel.setDividerLocation(150);
        this.add(this.mainPanel);

        // -------------------------
        this.boardPanel.updatePiecesInfo(Ludo.getInstance().getPiecesInformation());
    	this.boardPanel.updateBoardSquares(Ludo.getInstance().getBoardSquareArray());
    	
    	this.onGameStateChange();
    	this.setVisible(true);
    }
    
    public void onGameStateChange() {
    	Ludo ludo = Ludo.getInstance();
    	this.controlsPanel.changePlayer(ludo.getCurrentPlayer());
    	this.boardPanel.updatePiecesInfo(ludo.getPiecesInformation());
    	this.controlsPanel.updateDie(ludo.getDie());
    	this.controlsPanel.updateIfIsAllowedToRollDie(ludo.isRollActionPossible());
    	this.controlsPanel.updateIfIsAllowedToEndTurn(ludo.isEndTurnActionPossible());
    	if(ludo.hasPieceSelected()) {    		
    		this.boardPanel.updatePossibleMovements(ludo.getPlacesGivenPieceCanMove(ludo.getSelectedPiece()));
    	} else {
        	this.boardPanel.updatePossibleMovements(new Vector<PossiblePieceMovement>());
    	}
    }
 
}
