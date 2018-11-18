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
	
	public SubjectVoid onDiceRollButtonClick = new SubjectVoid();
	public SubjectVoid onNextTurnButtonClick = new SubjectVoid();
	
	public UIControlsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.currentPlayerLabel = new JLabel();
        this.firstDieLabel = new JLabel();
        this.secondDieLabel = new JLabel();

        UIControlsPanel other = this;
        
        this.rollDiceButton = new JButton("Roll");
        this.rollDiceButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickRollButton(); }
        });
        
        this.nextTurnButton = new JButton("Next Turn");
        this.nextTurnButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickTurnButton(); }
        });
         
        this.add(this.currentPlayerLabel);
        this.add(this.firstDieLabel);
        this.add(this.secondDieLabel);
        this.add(this.rollDiceButton);
        this.add(this.nextTurnButton);
        
        this.setCurrentPlayer(Ludo.getInstance().getCurrentPlayerName());
        this.setDice(3, 4);
	}
	
	public void onClickRollButton() {
		this.onDiceRollButtonClick.notifyAllObservers();
	}
	
	public void onClickTurnButton() {
		this.nextTurnButton.setEnabled(false);
		this.onNextTurnButtonClick.notifyAllObservers();
	}
	
	public void setCurrentPlayer(String playerName) {
		this.currentPlayerLabel.setText("Current Player = " + playerName);
	}
	
	public void setDice(int die1, int die2) {
		this.firstDieLabel.setText("Die 1 = " + die1);
		this.secondDieLabel.setText("Die 2 = " + die2);
	}
	
    public void changePlayer(Player player) {
    	this.setCurrentPlayer(player.getName());
    }
    
    public void allowEndTurn() {
		this.nextTurnButton.setEnabled(true);
    }

}
