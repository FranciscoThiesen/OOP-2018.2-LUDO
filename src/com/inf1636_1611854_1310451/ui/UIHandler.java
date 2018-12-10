package com.inf1636_1611854_1310451.ui;
import java.awt.*;
import javax.swing.JOptionPane;

import javax.swing.*;

import com.inf1636_1611854_1310451.game.BoardSquare;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.PieceMovement;
import com.inf1636_1611854_1310451.game.Player;
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
        this.controlsPanel = new UIControlsPanel();
        
        // -------------------------
        
        this.mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.controlsPanel, this.boardPanel);
        this.mainPanel.setOneTouchExpandable(true);
        this.mainPanel.setDividerLocation(150);
        this.add(this.mainPanel);

        // -------------------------
    	Ludo.getInstance().onStateChange.attach((Ludo ludoInstance) -> { this.onGameStateChange(); });
    	Ludo.getInstance().onPlayerWin.attach((Player player) -> { this.notifyPlayerVictory(player); });
    	
    	this.onGameStateChange();
    	this.setVisible(true);
    }
    
    public void notifyPlayerVictory(Player player) {
    	JOptionPane.showMessageDialog(this, "The player " + player.getName() + " has reached the end!");
    }
    
    public void onGameStateChange() {
    	Ludo ludo = Ludo.getInstance();
    	this.boardPanel.updateBoardSquares(Ludo.getInstance().getBoardSquareArray());
    	this.controlsPanel.changePlayer(ludo.getCurrentPlayer());
    	this.controlsPanel.updateDie(ludo.getDie());
    	this.controlsPanel.updateIfIsAllowedToRollDie(ludo.isRollActionPossible());
    	this.controlsPanel.updateIfIsAllowedToEndTurn(ludo.isEndTurnActionPossible());
    	if(ludo.hasPieceSelected()) {    		
    		this.boardPanel.updatePossibleMovements(ludo.getPlacesGivenPieceCanMove(ludo.getSelectedPiece()));
    	} else {
        	this.boardPanel.updatePossibleMovements(new Vector<PieceMovement>());
    	}
    }
 
}
