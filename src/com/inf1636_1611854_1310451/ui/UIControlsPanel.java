package com.inf1636_1611854_1310451.ui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.inf1636_1611854_1310451.game.BoardSquare;
import com.inf1636_1611854_1310451.game.Die;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.Player;
import com.inf1636_1611854_1310451.util.SubjectVoid;

import java.util.*;

public class UIControlsPanel extends JPanel {

	private JLabel currentPlayerLabel;
	private JLabel currentDieLabel;
	private UIAuxiliarPanel auxiliarPanel;
	private JButton rollDieButton;
	private JButton nextTurnButton;
	
	public SubjectVoid onDieRollButtonClick = new SubjectVoid();
	public SubjectVoid onNextTurnButtonClick = new SubjectVoid();
	
	public UIControlsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.currentPlayerLabel = new JLabel();
        this.currentDieLabel = new JLabel();
        this.auxiliarPanel = new UIAuxiliarPanel();

        UIControlsPanel other = this;
        
        this.rollDieButton = new JButton("Roll");
        this.rollDieButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickRollButton(); }
        });
        
        this.nextTurnButton = new JButton("Next Turn");
        this.nextTurnButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickTurnButton(); }
        });
         
        this.add(this.currentPlayerLabel);
        this.add(this.currentDieLabel);
        this.add(this.rollDieButton);
        this.add(this.nextTurnButton);
        this.add(this.auxiliarPanel);
	}
	
	public void onClickRollButton() {
		this.onDieRollButtonClick.notifyAllObservers();
	}
	
	public void onClickTurnButton() {
		this.nextTurnButton.setEnabled(false);
		this.onNextTurnButtonClick.notifyAllObservers();
	}
	
	public void setCurrentPlayer(String playerName) {
		this.currentPlayerLabel.setText("Current Player = " + playerName);
	}
	
	public void updateDie(Die die) {
		this.auxiliarPanel.updateDie(die);
		this.currentDieLabel.setText("Die Result = " + die.getValue() + (die.hasBeenUsed() ? " (used)" : ""));
	}
	
    public void changePlayer(Player player) {
    	this.setCurrentPlayer(player.getName());
    }
    
    public void allowEndTurn() {
		this.nextTurnButton.setEnabled(true);
    }
    
    public void updateIfIsAllowedToEndTurn(boolean isAllowedToEndTurn) {
    	if(isAllowedToEndTurn) {
    		this.nextTurnButton.setEnabled(true);
    	} else {
    		this.nextTurnButton.setEnabled(false);
    	}
    }
    
    public void updateIfIsAllowedToRollDie(boolean isAllowedToRollDie) {
    	if(isAllowedToRollDie) {
    		this.rollDieButton.setEnabled(true);
    	} else {
    		this.rollDieButton.setEnabled(false);
    	}
    }

}
