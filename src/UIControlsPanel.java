import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class UIControlsPanel extends JPanel {

	private JLabel currentPlayerLabel;
	private JLabel firstDieLabel;
	private JButton rollDieButton;
	private JButton nextTurnButton;
	
	public SubjectVoid onDieRollButtonClick = new SubjectVoid();
	public SubjectVoid onNextTurnButtonClick = new SubjectVoid();
	
	public UIControlsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.currentPlayerLabel = new JLabel();
        this.firstDieLabel = new JLabel();

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
        this.add(this.firstDieLabel);
        this.add(this.rollDieButton);
        this.add(this.nextTurnButton);
        
        this.setCurrentPlayer(Ludo.getInstance().getCurrentPlayerName());
        this.setDie(Ludo.getInstance().getDie());
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
	
	public void setDie(Die die) {
		this.firstDieLabel.setText("Die 1 = " + die.getValue() + (die.hasBeenUsed() ? " (used)":""));
	}
	
    public void changePlayer(Player player) {
    	this.setCurrentPlayer(player.getName());
    }
    
    public void allowEndTurn() {
		this.nextTurnButton.setEnabled(true);
    }

}
