import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class UIControlsPanel extends JPanel {

	private JLabel currentPlayerLabel;
	private JLabel firstDieLabel;
	private JLabel secondDieLabel;
	private JButton rollDiceButton;
	private JButton nextTurnButton;
	
	public UIControlsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.currentPlayerLabel = new JLabel();
        this.firstDieLabel = new JLabel();
        this.secondDieLabel = new JLabel();
        
        this.rollDiceButton = new JButton("Roll");
        this.nextTurnButton = new JButton("Next Turn");
         
        this.add(currentPlayerLabel);
        this.add(firstDieLabel);
        this.add(secondDieLabel);
        this.add(rollDiceButton);
        this.add(nextTurnButton);
        
        this.setCurrentPlayer("Ivan");
        this.setDice(3, 4);
	}
	
	public void setCurrentPlayer(String playerName) {
		this.currentPlayerLabel.setText("Current Player = " + playerName);
	}
	
	public void setDice(int die1, int die2) {
		this.firstDieLabel.setText("Die 1 = " + die1);
		this.secondDieLabel.setText("Die 2 = " + die2);
	}
}
