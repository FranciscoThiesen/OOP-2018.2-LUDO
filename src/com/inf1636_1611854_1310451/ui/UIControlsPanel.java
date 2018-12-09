package com.inf1636_1611854_1310451.ui;
import java.awt.event.*;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.inf1636_1611854_1310451.game.Die;
import com.inf1636_1611854_1310451.game.Ludo;
import com.inf1636_1611854_1310451.game.Piece;
import com.inf1636_1611854_1310451.game.Player;
import com.inf1636_1611854_1310451.util.SubjectVoid;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class UIControlsPanel extends JPanel {

	private static final long serialVersionUID = 4404630913178001744L;
	private JLabel currentPlayerLabel;
	private JLabel currentDieLabel;
	private UIAuxiliarPanel auxiliarPanel;
	private JButton rollDieButton;
	private JButton nextTurnButton;
	private JButton saveGameButton;
	private JButton loadGameButton;
	
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
        
        this.saveGameButton = new JButton("Save Game");
        this.saveGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickSaveButton(); }
        });
        
        this.loadGameButton = new JButton("Load Game");
        this.loadGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e){ other.onClickLoadButton(); }
        });
         
        this.add(this.currentPlayerLabel);
        this.add(this.currentDieLabel);
        this.add(this.rollDieButton);
        this.add(this.nextTurnButton);
        this.add(this.saveGameButton);
        this.add(this.loadGameButton);
        this.add(this.auxiliarPanel);
	}
	
	public void onClickRollButton() {
		Ludo.getInstance().rollDie();
	}
	
	
	/*
	 * 
	 	@SuppressWarnings("unchecked")
	public JSONObject saveStateToJSON() {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for(Piece piece: this.pieces) {			
			array.add(piece.saveStateToString());
		}
		obj.put("name", this.name);
		obj.put("pieces", array);
		return obj.toJSONString();
	}
	
	public void loadStateFromJSON(JSONObject obj) {
	      JSONParser parser = new JSONParser();
	      try{
	    	JSONObject obj = (JSONObject) parser.parse(str);
	    	JSONArray array = (JSONArray) obj.get("pieces");
			for(int i=0; i<array.size(); i++) {			
				this.pieces.get(i).loadStateFromString((String) array.get(i));
			}
	    	this.name = (String) obj.get("name");
	      }catch(ParseException pe){
	          System.out.println("Error loading Player state from JSON.");
	       }
	}* 
	 */
	
	public void onClickSaveButton() {
		try {
			JFileChooser fileChooser = new JFileChooser();
	        int returnVal = fileChooser.showOpenDialog(this);
	
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            FileWriter writer = new FileWriter(file);
	            writer.write(Ludo.getInstance().saveStateToJSON().toJSONString());
	            writer.close();
	        }
		} catch(Exception e) {
			System.out.println("Error saving file.");
		}
	}
	
	public void onClickLoadButton() {
		try {
			JFileChooser fileChooser = new JFileChooser();
	        int returnVal = fileChooser.showOpenDialog(this);
		    JSONParser parser = new JSONParser();
	
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            Scanner scanner = new Scanner(file);
	            String str = scanner.next();
	            JSONObject obj = (JSONObject) parser.parse(str);
	            Ludo.getInstance().loadStateFromJSON(obj);
	            scanner.close();
	        }
	        System.out.println("Game loaded!");
		} catch(Exception e) {
			System.out.println("Error loading file. ERROR:" + e.toString());
			e.printStackTrace();
		}
	}
	
	public void onClickTurnButton() {
		this.nextTurnButton.setEnabled(false);
		Ludo.getInstance().endTurn();
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
